package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final CidadeService cidadeService;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository,
                              CidadeService cidadeService) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.cidadeService = cidadeService;
    }

    @Transactional
    public AreaAgricolaDto criarAreaAgricola(AreaAgricolaDto areaAgricolaDto) {
        AreaAgricola areaAgricola = convertToEntity(areaAgricolaDto);
        AreaAgricola savedAreaAgricola = areaAgricolaRepository.save(areaAgricola);
        return convertToDto(savedAreaAgricola);
    }

    public List<AreaAgricolaDto> listarAreasAgricolas() {
        List<AreaAgricola> areasAgricolas = areaAgricolaRepository.findAll();
        return areasAgricolas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AreaAgricolaDto buscarAreaAgricolaPorId(Long id) {
        Optional<AreaAgricola> areaAgricolaOpt = areaAgricolaRepository.findById(id);
        if (areaAgricolaOpt.isPresent()) {
            return convertToDto(areaAgricolaOpt.get());
        }
        throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
    }

    @Transactional
    public AreaAgricolaDto atualizarAreaAgricola(Long id, AreaAgricolaDto areaAgricolaDto) {
        if (!areaAgricolaRepository.existsById(id)) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
        }
        areaAgricolaDto.setId(id);
        AreaAgricola areaAgricola = convertToEntity(areaAgricolaDto);
        AreaAgricola updatedAreaAgricola = areaAgricolaRepository.save(areaAgricola);
        return convertToDto(updatedAreaAgricola);
    }

    @Transactional
    public void removerAreaAgricola(Long id) {
        if (!areaAgricolaRepository.existsById(id)) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
        }
        areaAgricolaRepository.deleteById(id);
    }

    private AreaAgricola convertToEntity(AreaAgricolaDto dto) {
        AreaAgricola areaAgricola = new AreaAgricola();
        areaAgricola.setId(dto.getId());
        
        String cidadeNome = dto.getCidadeNome();
        if (cidadeNome == null || cidadeNome.isEmpty()) {
            try {
                java.lang.reflect.Method getCidadeMethod = dto.getClass().getMethod("getCidade");
                cidadeNome = (String) getCidadeMethod.invoke(dto);
            } catch (Exception e) {
            }
        }
        
        if (cidadeNome != null && !cidadeNome.isEmpty()) {
            Cidade cidade = cidadeService.buscarOuCriar(cidadeNome);
            areaAgricola.setCidade(cidade);
        }
        
        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());
        
        if (dto.getStatus() != null) {
            areaAgricola.setStatus(dto.getStatus());
        } else {
            areaAgricola.setStatus(StatusArea.Pendente);
        }

        if (dto.getArquivoFazenda() != null && !dto.getArquivoFazenda().isEmpty()) {
            try {
                Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(dto.getArquivoFazenda());
                areaAgricola.setArquivoFazenda(geometry);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar o arquivo GeoJSON", e);
            }
        }
        
        return areaAgricola;
    }

    private AreaAgricolaDto convertToDto(AreaAgricola areaAgricola) {
        AreaAgricolaDto dto = new AreaAgricolaDto();
        dto.setId(areaAgricola.getId());
        
        if (areaAgricola.getCidade() != null) {
            dto.setCidadeNome(areaAgricola.getCidade().getNome());
            try {
                java.lang.reflect.Method setCidadeMethod = dto.getClass().getMethod("setCidade", String.class);
                setCidadeMethod.invoke(dto, areaAgricola.getCidade().getNome());
            } catch (Exception e) {
            }
        }
        
        dto.setNomeFazenda(areaAgricola.getNomeFazenda());
        
        try {
            java.lang.reflect.Method setNomeFazendaMethod = 
                dto.getClass().getMethod("setNomeFazenda", String.class);
            setNomeFazendaMethod.invoke(dto, areaAgricola.getNomeFazenda());
        } catch (Exception e) {
        }
        
        dto.setEstado(areaAgricola.getEstado());
        dto.setStatus(areaAgricola.getStatus());
        
        if (areaAgricola.getArquivoFazenda() != null) {
            String geoJson = conversorGeoJson.convertGeometryToGeoJson(areaAgricola.getArquivoFazenda());
            dto.setArquivoFazenda(geoJson);
        }
        
        return dto;
    }
}
