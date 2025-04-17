package fatec.porygon.service;

import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Safra;
import fatec.porygon.entity.Talhao;
import fatec.porygon.entity.TipoSolo;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public TalhaoDto criarTalhao(TalhaoDto talhaoDto) {
        Talhao talhao = convertToEntity(talhaoDto);
        Talhao savedTalhao = talhaoRepository.save(talhao);

        Safra safra = new Safra();
        safra.setAno(talhaoDto.getAno());
        safra.setStatus(talhaoDto.getStatus());
        safra.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));
        safra.setTalhao(savedTalhao);

        if (talhaoDto.getArquivoDaninha() != null && !talhaoDto.getArquivoDaninha().isEmpty()) {
            safra.setArquivoDaninha(conversorGeoJson.convertGeoJsonToGeometry(talhaoDto.getArquivoDaninha()));
        }
        if (talhaoDto.getArquivoFinalDaninha() != null && !talhaoDto.getArquivoFinalDaninha().isEmpty()) {
            safra.setArquivoFinalDaninha(conversorGeoJson.convertGeoJsonToGeometry(talhaoDto.getArquivoFinalDaninha()));
        }

        safraService.criarSafra(safra);

        return convertToDto(savedTalhao);
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
    public TalhaoDto atualizarTalhao(Long id, TalhaoDto talhaoDto) {
        if (!talhaoRepository.existsById(id)) {
            throw new RuntimeException("Talhão não encontrado com ID: " + id);
        }
        talhaoDto.setId(id);
        Talhao talhao = convertToEntity(talhaoDto);
        Talhao updatedTalhao = talhaoRepository.save(talhao);
    
        // Atualiza todas as safras associadas ao talhão
        List<Safra> safras = safraService.buscarPorTalhao(id);
        for (Safra safra : safras) {
            safra.setAno(talhaoDto.getAno());
            safra.setStatus(talhaoDto.getStatus());
            safra.setCultura(culturaService.buscarOuCriar(talhaoDto.getCulturaNome()));
            safra.setTalhao(updatedTalhao);
    
            if (talhaoDto.getArquivoDaninha() != null && !talhaoDto.getArquivoDaninha().isEmpty()) {
                safra.setArquivoDaninha(conversorGeoJson.convertGeoJsonToGeometry(talhaoDto.getArquivoDaninha()));
            }
            if (talhaoDto.getArquivoFinalDaninha() != null && !talhaoDto.getArquivoFinalDaninha().isEmpty()) {
                safra.setArquivoFinalDaninha(conversorGeoJson.convertGeoJsonToGeometry(talhaoDto.getArquivoFinalDaninha()));
            }
    
            safraService.criarSafra(safra);
        }
    
        return convertToDto(updatedTalhao);
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
        talhao.setId(dto.getId());
        talhao.setArea(dto.getArea());

        TipoSolo tipoSolo = tipoSoloService.buscarOuCriar(dto.getTipoSolo().toString());
        talhao.setTipoSolo(tipoSolo);

        AreaAgricola areaAgricola = areaAgricolaService.buscarAreaAgricolaEntityPorId(dto.getAreaAgricola());
        talhao.setAreaAgricola(areaAgricola);
        if (areaAgricola == null) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + dto.getAreaAgricola());
        }
        talhao.setAreaAgricola(areaAgricola);

        return talhao;
    }

    private TalhaoDto convertToDto(Talhao talhao) {
        TalhaoDto dto = new TalhaoDto();
        dto.setId(talhao.getId());
        dto.setArea(talhao.getArea());
    
        if (talhao.getTipoSolo() != null) {
            dto.setTipoSolo(talhao.getTipoSolo().getId());
        }
    
        if (talhao.getAreaAgricola() != null) {
            dto.setAreaAgricola(talhao.getAreaAgricola().getId());
        }
    
        List<Safra> safras = safraService.buscarPorTalhao(talhao.getId());
        dto.setSafras(safras);

        return dto;
    }
}
