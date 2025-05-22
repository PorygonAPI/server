package fatec.porygon.service;

import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Safra;
import fatec.porygon.repository.TalhaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import fatec.porygon.entity.Talhao;
import fatec.porygon.entity.TipoSolo;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.Geometry;

@Service
public class TalhaoService {

    private final TalhaoRepository talhaoRepository;
    private final CulturaService culturaService;
    private final TipoSoloService tipoSoloService;
    private final AreaAgricolaService areaAgricolaService;
    private final SafraService safraService;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();

    @Autowired
    public TalhaoService(TalhaoRepository talhaoRepository,
            CulturaService culturaService,
            TipoSoloService tipoSoloService,
            AreaAgricolaService areaAgricolaService,
            SafraService safraService) {
        this.talhaoRepository = talhaoRepository;
        this.culturaService = culturaService;
        this.tipoSoloService = tipoSoloService;
        this.areaAgricolaService = areaAgricolaService;
        this.safraService = safraService;
    }

    @Transactional
    public TalhaoDto criarTalhao(TalhaoDto talhaoDto, MultipartFile arquivoDaninha) {
        try {
            // Create and save Talhao with all relationships loaded
            Talhao talhao = convertToEntity(talhaoDto);
            Talhao savedTalhao = talhaoRepository.save(talhao);

            // Create Safra with all required fields
            Safra safra = new Safra();
            safra.setId(java.util.UUID.randomUUID().toString());
            safra.setAno(talhaoDto.getAno());
            safra.setStatus(StatusSafra.Pendente);
            safra.setProdutividadeAno(0.0); // Set default value
            
            // Set relationships
            safra.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));
            safra.setTalhao(savedTalhao);
            
            // Set timestamps
            LocalDateTime now = LocalDateTime.now();
            safra.setDataCadastro(now);
            safra.setDataUltimaVersao(now);
            
            // Process GeoJSON file if provided
            if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                safra.setArquivoDaninha(geometria);
            }

            // Save Safra
            Safra savedSafra = safraService.criar(safra, arquivoDaninha);

            // Convert to DTO with loaded relationships
            TalhaoDto result = convertToDto(savedTalhao);
            result.setStatus(savedSafra.getStatus());
            result.setAno(savedSafra.getAno());
            
            return result;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar talhão: " + e.getMessage(), e);
        }
    }

    public List<TalhaoDto> listarTodos() {
        List<Talhao> talhoes = talhaoRepository.findAll();
        return talhoes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TalhaoDto buscarPorId(Long id) {
        Talhao talhao = talhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + id));
        return convertToDto(talhao);
    }

    @Transactional
public TalhaoDto atualizarTalhao(Long id, TalhaoDto talhaoDto, MultipartFile arquivoDaninha) {
    try {
        // Busca o talhão existente
        Talhao talhao = talhaoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado com ID: " + id));
        
        // Atualiza dados básicos do talhão
        talhao.setArea(talhaoDto.getArea());
        talhao.setTipoSolo(tipoSoloService.buscarOuCriar(talhaoDto.getTipoSoloNome()));
        
        if (talhaoDto.getAreaAgricola() != null) {
            AreaAgricola areaAgricola = areaAgricolaService.buscarAreaAgricolaEntityPorId(talhaoDto.getAreaAgricola());
            if (areaAgricola == null) {
                throw new EntityNotFoundException("Área Agrícola não encontrada");
            }
            talhao.setAreaAgricola(areaAgricola);
        }

        // Atualiza a safra existente em vez de criar uma nova
        List<Safra> safras = safraService.buscarPorTalhao(id);
        if (!safras.isEmpty()) {
            // Atualiza a primeira safra encontrada
            Safra safraExistente = safras.get(0);
            safraExistente.setAno(talhaoDto.getAno());
            safraExistente.setStatus(talhaoDto.getStatus());
            safraExistente.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));
            
            // Atualiza arquivo daninha se fornecido
            if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                safraExistente.setArquivoDaninha(geometria);
                safraExistente.setDataUltimaVersao(LocalDateTime.now());
            }
            
            // Salva a safra atualizada
            safraService.atualizar(safraExistente.getId(), safraExistente);
        }

        // Salva o talhão atualizado
        Talhao talhaoAtualizado = talhaoRepository.save(talhao);
        return convertToDto(talhaoAtualizado);
        
    } catch (Exception e) {
        throw new RuntimeException("Erro ao atualizar talhão: " + e.getMessage(), e);
    }
}

    @Transactional
    public void deletar(Long id) {
        if (!talhaoRepository.existsById(id)) {
            throw new RuntimeException("Talhão não encontrado com ID: " + id);
        }
        talhaoRepository.deleteById(id);
    }

    private Talhao convertToEntity(TalhaoDto dto) {
    Talhao talhao = new Talhao();
    
    // Configurar campos básicos
    talhao.setArea(dto.getArea());

    // Configurar TipoSolo
    if (dto.getTipoSoloNome() != null && !dto.getTipoSoloNome().trim().isEmpty()) {
        TipoSolo tipoSolo = tipoSoloService.buscarOuCriar(dto.getTipoSoloNome());
        talhao.setTipoSolo(tipoSolo);
    }

    // Configurar AreaAgricola
    if (dto.getAreaAgricola() != null) {
        AreaAgricola areaAgricola = areaAgricolaService.buscarAreaAgricolaEntityPorId(dto.getAreaAgricola());
        if (areaAgricola == null) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + dto.getAreaAgricola());
        }
        talhao.setAreaAgricola(areaAgricola);
    } else {
        throw new RuntimeException("ID da área agrícola é obrigatório");
    }

    return talhao;
}

    private TalhaoDto convertToDto(Talhao talhao) {
        TalhaoDto dto = new TalhaoDto();
        dto.setId(talhao.getId());
        dto.setArea(talhao.getArea());

        if (talhao.getAreaAgricola() != null) {
            dto.setAreaAgricola(talhao.getAreaAgricola().getId());
        }

        if (talhao.getTipoSolo() != null) {
            dto.setTipoSoloNome(talhao.getTipoSolo().getTipoSolo());
        }

        // Get latest safra eagerly
        List<Safra> safras = safraService.buscarPorTalhao(talhao.getId());
        if (!safras.isEmpty()) {
            Safra safraAtual = safras.get(0);
            dto.setAno(safraAtual.getAno());
            dto.setStatus(safraAtual.getStatus());
            dto.setProdutividadeAno(safraAtual.getProdutividadeAno() != null ? 
                safraAtual.getProdutividadeAno().floatValue() : 0.0f);
            
            if (safraAtual.getCultura() != null) {
                dto.setCultura(safraAtual.getCultura().getId());
                dto.setCulturaNome(safraAtual.getCultura().getNome());
            }
            
            if (safraAtual.getArquivoDaninha() != null) {
                dto.setArquivoDaninha(conversorGeoJson.convertGeometryToGeoJson(
                    safraAtual.getArquivoDaninha()));
            }
        }

        return dto;
    }

    public void editarTalhaoPorAreaAgricola(Long idTalhao, String idSafra, Long idAreaAgricola) {
        var safra = talhaoRepository.findById(idTalhao);

    }

}
