package fatec.porygon.service;

import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Safra;
import fatec.porygon.repository.TalhaoRepository;
import org.springframework.stereotype.Service;
import fatec.porygon.entity.Talhao;
import fatec.porygon.entity.TipoSolo;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @Transactional
    public TalhaoDto criarTalhao(TalhaoDto talhaoDto, MultipartFile arquivoDaninha) {
        try {
            Talhao talhao = convertToEntity(talhaoDto);
            Talhao savedTalhao = talhaoRepository.save(talhao);

            Safra safra = new Safra();
            safra.setId(java.util.UUID.randomUUID().toString());
            safra.setAno(talhaoDto.getAno());
            safra.setStatus(StatusSafra.Pendente);
            safra.setProdutividadeAno(0.0);

            safra.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));
            safra.setTalhao(savedTalhao);

            LocalDateTime now = LocalDateTime.now();
            safra.setDataCadastro(now);
            safra.setDataUltimaVersao(now);

            if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                safra.setArquivoDaninha(geometria);
            }

            Safra savedSafra = safraService.criar(safra, arquivoDaninha);

            TalhaoDto result = convertToDto(savedTalhao);
            result.setStatus(savedSafra.getStatus());
            result.setAno(savedSafra.getAno());

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar talhão: " + e.getMessage(), e);
        }
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<TalhaoDto> listarTodos() {
        List<Talhao> talhoes = talhaoRepository.findAll();
        return talhoes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public TalhaoDto buscarPorId(Long id) {
        Talhao talhao = talhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + id));
        return convertToDto(talhao);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @Transactional
    public TalhaoDto atualizarTalhao(Long id, TalhaoDto talhaoDto, MultipartFile arquivoDaninha) {
        try {
            Talhao talhao = talhaoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado com ID: " + id));

            talhao.setArea(talhaoDto.getArea());
            talhao.setTipoSolo(tipoSoloService.buscarOuCriar(talhaoDto.getTipoSoloNome()));

            if (talhaoDto.getAreaAgricola() != null) {
                AreaAgricola areaAgricola = areaAgricolaService
                        .buscarAreaAgricolaEntityPorId(talhaoDto.getAreaAgricola());
                if (areaAgricola == null) {
                    throw new EntityNotFoundException("Área Agrícola não encontrada");
                }
                talhao.setAreaAgricola(areaAgricola);
            }

            List<Safra> safras = safraService.buscarPorTalhao(id);
            if (!safras.isEmpty()) {
                Safra safraExistente = safras.get(0);
                safraExistente.setAno(talhaoDto.getAno());
                safraExistente.setStatus(talhaoDto.getStatus());
                safraExistente.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));

                if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                    String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                    Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                    safraExistente.setArquivoDaninha(geometria);
                    safraExistente.setDataUltimaVersao(LocalDateTime.now());
                }

                safraService.atualizar(safraExistente.getId(), safraExistente);
            }

            Talhao talhaoAtualizado = talhaoRepository.save(talhao);
            return convertToDto(talhaoAtualizado);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar talhão: " + e.getMessage(), e);
        }
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @Transactional
    public void deletar(Long id) {
        if (!talhaoRepository.existsById(id)) {
            throw new RuntimeException("Talhão não encontrado com ID: " + id);
        }
        talhaoRepository.deleteById(id);
    }

    private Talhao convertToEntity(TalhaoDto dto) {
        Talhao talhao = new Talhao();

        talhao.setArea(dto.getArea());

        if (dto.getTipoSoloNome() != null && !dto.getTipoSoloNome().trim().isEmpty()) {
            TipoSolo tipoSolo = tipoSoloService.buscarOuCriar(dto.getTipoSoloNome());
            talhao.setTipoSolo(tipoSolo);
        }

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
    dto.setAreaAgricola(talhao.getAreaAgricola().getId());

    try {
        List<Safra> safras = safraService.buscarPorTalhao(talhao.getId());
        if (!safras.isEmpty()) {
            Safra safraAtual = safras.get(0);
            dto.setCulturaNome(safraAtual.getCultura().getNome());
            dto.setAno(safraAtual.getAno());
            dto.setStatus(safraAtual.getStatus());
            dto.setProdutividadeAno(safraAtual.getProdutividadeAno() != null ? safraAtual.getProdutividadeAno().floatValue() : 0.0f);
        }
    } catch (RuntimeException e) {
        dto.setCulturaNome("");
        dto.setAno(LocalDateTime.now().getYear());
        dto.setStatus(StatusSafra.Pendente);
        dto.setProdutividadeAno(0.0f);
    }
    
    return dto;
}

    @SuppressWarnings("unused")
    public void editarTalhaoPorAreaAgricola(Long idTalhao, String idSafra, Long idAreaAgricola) {
        var safra = talhaoRepository.findById(idTalhao);

    }

}
