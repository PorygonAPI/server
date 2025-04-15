package fatec.porygon.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.entity.Talhao;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final TalhaoRepository talhaoRepository;
    private final CidadeService cidadeService;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository,
                              TalhaoRepository talhaoRepository,
                              CidadeService cidadeService) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.talhaoRepository = talhaoRepository;
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

    @Transactional
    public void processarTalhoesGeoJson(MultipartFile geoJsonFile, AreaAgricola areaAgricola) {
        try {
            String geoJsonContent = new String(geoJsonFile.getBytes());
            JsonNode rootNode = objectMapper.readTree(geoJsonContent);
            
            if (!rootNode.has("features")) {
                throw new RuntimeException("Arquivo GeoJSON inválido: não contém features");
            }

            JsonNode features = rootNode.get("features");
            for (JsonNode feature : features) {
                processarFeature(feature, areaAgricola);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo GeoJSON", e);
        }
    }

    private void processarFeature(JsonNode feature, AreaAgricola areaAgricola) {
        if (!feature.has("properties") || !feature.has("geometry")) {
            throw new RuntimeException("Feature inválida: faltam propriedades ou geometria");
        }

        JsonNode properties = feature.get("properties");
        if (!properties.has("MN_TL")) {
            throw new RuntimeException("Feature inválida: falta propriedade MN_TL");
        }

        String mnTl = properties.get("MN_TL").asText();
        
        // Verifica se já existe um talhão com esse MN_TL
        if (talhaoRepository.findByMnTlAndAreaAgricola(mnTl, areaAgricola).isEmpty()) {
            Talhao novoTalhao = new Talhao();
            novoTalhao.setMnTl(mnTl);
            novoTalhao.setAreaAgricola(areaAgricola);

            try {
                // Converte a geometria da feature para o formato adequado
                Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(
                    objectMapper.writeValueAsString(feature.get("geometry"))
                );
                novoTalhao.setGeometria(geometry);
                
                // Calcula a área em hectares (assumindo que a geometria está em metros quadrados)
                double areaHectares = geometry.getArea() / 10000.0;
                novoTalhao.setArea(areaHectares);
                
                talhaoRepository.save(novoTalhao);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar geometria do talhão: " + mnTl, e);
            }
        }
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

        // Conversão de GeoJSON para Geometry
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
        
        // Conversão de Geometry para GeoJSON
        if (areaAgricola.getArquivoFazenda() != null) {
            String geoJson = conversorGeoJson.convertGeometryToGeoJson(areaAgricola.getArquivoFazenda());
            dto.setArquivoFazenda(geoJson);
        }
        
        return dto;
    }
}
