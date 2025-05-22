package fatec.porygon.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.dto.CadastroAreaAgricolaDto;
import fatec.porygon.entity.*;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.*;
import fatec.porygon.utils.ConvertGeoJsonUtils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
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

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @Transactional
    public AreaAgricolaDto criarAreaAgricolaECriarSafra(CadastroAreaAgricolaDto dto) {
        Cidade cidade = cidadeService.buscarOuCriar(dto.getCidadeNome());

        AreaAgricola areaAgricola = new AreaAgricola();
        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());
        areaAgricola.setCidade(cidade);
        areaAgricola.setStatus(StatusArea.Pendente);

        try {
            ObjectMapper mapper = new ObjectMapper();
            String geoJsonContent = new String(dto.getArquivoFazenda().getBytes());
            JsonNode rootNode = mapper.readTree(geoJsonContent);

            GeometryFactory geometryFactory = new GeometryFactory();
            GeoJsonReader geoJsonReader = new GeoJsonReader(geometryFactory);

            Geometry merged = null;

            for (JsonNode feature : rootNode.get("features")) {
                JsonNode geometryNode = feature.get("geometry");

                String geometryJson = geometryNode.toString();

                Geometry geometry = geoJsonReader.read(geometryJson);

                if (merged == null) {
                    merged = geometry;
                } else {
                    merged = merged.union(geometry);
                }
            }
            areaAgricola.setArquivoFazenda(merged);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo GeoJSON da fazenda", e);
        }

        AreaAgricola savedAreaAgricola = areaAgricolaRepository.save(areaAgricola);
        processarTalhoesGeoJson(dto.getArquivoFazenda(), savedAreaAgricola, dto.getArquivoErvaDaninha());

        return convertToDto(savedAreaAgricola);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<AreaAgricolaDto> listarAreasAgricolas() {
        List<AreaAgricola> areasAgricolas = areaAgricolaRepository.findAll();
        return areasAgricolas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public AreaAgricolaDto buscarAreaAgricolaPorId(Long id) {
        Optional<AreaAgricola> areaAgricolaOpt = areaAgricolaRepository.findById(id);
        if (areaAgricolaOpt.isPresent()) {
            return convertToDto(areaAgricolaOpt.get());
        }
        throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
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

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
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

    public void processarTalhaoFeature(JsonNode feature, AreaAgricola areaAgricola, Geometry geometryErvaDaninha) {
        if (!feature.has("properties")) {
            throw new RuntimeException("Feature inválida: faltam propriedades");
        }

        JsonNode properties = feature.get("properties");
        if (!properties.has("AREA_HA_TL")) {
            throw new RuntimeException("Feature inválida: falta propriedade AREA_HA_TL");
        }

        String idSafra = properties.get("MN_TL").asText();
        String nomeCultura = properties.get("CULTURA").asText();
        String nomeCulturaCap = nomeCultura.substring(0, 1).toUpperCase() + nomeCultura.substring(1).toLowerCase();

        String nomeTipoSolo = properties.get("SOLO").asText();
        String nomeTipoSoloCap = nomeTipoSolo.substring(0, 1).toUpperCase() + nomeTipoSolo.substring(1).toLowerCase();

        String anoSafra = properties.get("SAFRA").asText();


        Double talhaoArea = properties.get("AREA_HA_TL").asDouble();
        Optional<Cultura> cultura = culturaRepository.findByNome(nomeCulturaCap);
        Optional<TipoSolo> tipoSolo = tipoSoloRepository.findByTipoSolo(nomeTipoSoloCap);

        Talhao novoTalhao = new Talhao();

        novoTalhao.setAreaAgricola(areaAgricola);
        novoTalhao.setArea(talhaoArea);
        novoTalhao.setTipoSolo(tipoSolo.orElseThrow());

        Talhao talhaoSalvo = talhaoRepository.save(novoTalhao);

        Safra novaSafra = safraRepository.findById(idSafra)
                .orElseGet(() -> {
                    Safra safra = new Safra();
                    safra.setId(idSafra);
                    return safra;
                });

        novaSafra.setTalhao(talhaoSalvo);
        novaSafra.setAno(Integer.parseInt(anoSafra.split("/")[0]));
        novaSafra.setStatus(StatusSafra.Pendente);
        novaSafra.setCultura(cultura.orElseThrow());
        novaSafra.setArquivoDaninha(geometryErvaDaninha);
        novaSafra.setDataCadastro(LocalDateTime.now());
        novaSafra.setDataUltimaVersao(LocalDateTime.now());


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