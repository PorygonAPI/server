package fatec.porygon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.dto.CadastroAreaAgricolaDto;
import fatec.porygon.entity.*;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.*;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.utils.ConvertGeoJsonUtils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final CidadeService cidadeService;
    private final SafraRepository safraRepository;
    private final TalhaoRepository talhaoRepository;
    private final CulturaRepository culturaRepository;
    private final TipoSoloRepository tipoSoloRepository;

    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository,
                               CidadeService cidadeService,
                               SafraRepository safraRepository,
                               TalhaoRepository talhaoRepository,
                               CulturaRepository culturaRepository,
                               TipoSoloRepository tipoSoloRepository) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.cidadeService = cidadeService;
        this.safraRepository = safraRepository;
        this.talhaoRepository = talhaoRepository;
        this.culturaRepository = culturaRepository;
        this.tipoSoloRepository = tipoSoloRepository;
    }

    @Transactional
    public AreaAgricolaDto criarAreaAgricolaECriarSafra(CadastroAreaAgricolaDto dto) {
    try {
        // Process GeoJSON files first
        String geoJsonContent = new String(dto.getArquivoFazenda().getBytes(), StandardCharsets.UTF_8);
        String ervaDaninhaContent = new String(dto.getArquivoErvaDaninha().getBytes(), StandardCharsets.UTF_8);

        // Create and configure AreaAgricola
        Cidade cidade = cidadeService.buscarOuCriar(dto.getCidadeNome());
        AreaAgricola areaAgricola = new AreaAgricola();
        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());
        areaAgricola.setCidade(cidade);
        areaAgricola.setStatus(StatusArea.Pendente);

        // Convert and set arquivo_fazenda with error handling
        try {
            Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(geoJsonContent);
            if (geometry == null) {
                throw new RuntimeException("Geometria inválida no arquivo GeoJSON");
            }
            areaAgricola.setArquivoFazenda(geometry);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo GeoJSON da fazenda: " + e.getMessage(), e);
        }

        // Save AreaAgricola
        AreaAgricola savedAreaAgricola = areaAgricolaRepository.save(areaAgricola);

        // Process talhoes
        processarTalhoesGeoJson(geoJsonContent, savedAreaAgricola, ervaDaninhaContent);

        return convertToDto(savedAreaAgricola);
    } catch (IOException e) {
        throw new RuntimeException("Erro ao processar os arquivos: " + e.getMessage(), e);
    }
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

    public void processarTalhoesGeoJson(String arquivo, AreaAgricola areaAgricola, String ervaDaninha) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(arquivo);

            if (!rootNode.has("features")) {
                throw new RuntimeException("Arquivo GeoJSON inválido: não contém features");
            }


            JsonNode root = mapper.readTree(ervaDaninha);

            GeometryFactory geometryFactory = new GeometryFactory();
            GeoJsonReader geoJsonReader = new GeoJsonReader(geometryFactory);

            Geometry merged = null;

            for (JsonNode feature : root.get("features")) {
                JsonNode geometryNode = feature.get("geometry");

                String geometryJson = geometryNode.toString();

                Geometry geometry = geoJsonReader.read(geometryJson);

                if (merged == null) {
                    merged = geometry;
                } else {
                    merged = merged.union(geometry);
                }
            }


            JsonNode features = rootNode.get("features");
            for (JsonNode feature : features) {
                processarTalhaoFeature(feature, areaAgricola, merged);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Erro ao processar arquivo GeoJSON: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void processarTalhaoFeature(JsonNode feature, AreaAgricola areaAgricola, Geometry geometryErvaDaninha) {
        // Get properties from feature
        JsonNode properties = feature.get("properties");
        
        // Create and save Talhao first
        Talhao novoTalhao = new Talhao();
        novoTalhao.setAreaAgricola(areaAgricola);
        novoTalhao.setArea(properties.get("AREA_HA_TL").asDouble());
        
        // Get and set TipoSolo
        String nomeTipoSolo = properties.get("SOLO").asText();
        String nomeTipoSoloCap = nomeTipoSolo.substring(0, 1).toUpperCase() + nomeTipoSolo.substring(1).toLowerCase();
        TipoSolo tipoSolo = tipoSoloRepository.findByTipoSolo(nomeTipoSoloCap)
            .orElseThrow(() -> new RuntimeException("TipoSolo não encontrado: " + nomeTipoSoloCap));
        novoTalhao.setTipoSolo(tipoSolo);
        
        // Save Talhao
        Talhao talhaoSalvo = talhaoRepository.save(novoTalhao);

        // Create and save Safra
        String idSafra = properties.get("MN_TL").asText();
        Safra novaSafra = new Safra();
        novaSafra.setId(idSafra);
        novaSafra.setTalhao(talhaoSalvo);
        novaSafra.setAno(Integer.parseInt(properties.get("SAFRA").asText().split("/")[0]));
        novaSafra.setStatus(StatusSafra.Pendente);
        
        // Get and set Cultura
        String nomeCultura = properties.get("CULTURA").asText();
        String nomeCulturaCap = nomeCultura.substring(0, 1).toUpperCase() + nomeCultura.substring(1).toLowerCase();
        Cultura cultura = culturaRepository.findByNome(nomeCulturaCap)
            .orElseThrow(() -> new RuntimeException("Cultura não encontrada: " + nomeCulturaCap));
        novaSafra.setCultura(cultura);
        
        novaSafra.setArquivoDaninha(geometryErvaDaninha);
        novaSafra.setDataCadastro(LocalDateTime.now());
        novaSafra.setDataUltimaVersao(LocalDateTime.now());
        
        // Save Safra
        safraRepository.save(novaSafra);
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

    public AreaAgricola buscarAreaAgricolaEntityPorId(Long id) {
        return areaAgricolaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área agrícola não encontrada com ID: " + id));
    }
    
}