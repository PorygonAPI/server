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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
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

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @Transactional
    public AreaAgricolaDto criarAreaAgricolaECriarSafra(CadastroAreaAgricolaDto dto) {
        try {
            String geoJsonContent = new String(dto.getArquivoFazenda().getBytes(), StandardCharsets.UTF_8);
            String ervaDaninhaContent = new String(dto.getArquivoErvaDaninha().getBytes(), StandardCharsets.UTF_8);

            Cidade cidade = cidadeService.buscarOuCriar(dto.getCidadeNome());
            AreaAgricola areaAgricola = new AreaAgricola();
            areaAgricola.setNomeFazenda(dto.getNomeFazenda());
            areaAgricola.setEstado(dto.getEstado());
            areaAgricola.setCidade(cidade);
            areaAgricola.setStatus(StatusArea.Pendente);

            try {
                Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(geoJsonContent);
                if (geometry == null) {
                    throw new RuntimeException("Geometria inválida no arquivo GeoJSON");
                }
                areaAgricola.setArquivoFazenda(geometry);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar o arquivo GeoJSON da fazenda: " + e.getMessage(), e);
            }

            AreaAgricola savedAreaAgricola = areaAgricolaRepository.save(areaAgricola);

            processarTalhoesGeoJson(geoJsonContent, savedAreaAgricola, ervaDaninhaContent);

            return convertToDto(savedAreaAgricola);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar os arquivos: " + e.getMessage(), e);
        }
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
    public AreaAgricolaDto atualizarAreaAgricola(
            Long id,
            AreaAgricolaDto areaAgricolaDto,
            MultipartFile arquivoFazenda,
            MultipartFile arquivoErvaDaninha) {

        AreaAgricola existingArea = areaAgricolaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área agrícola não encontrada: " + id));

        existingArea.setNomeFazenda(areaAgricolaDto.getNomeFazenda());
        existingArea.setEstado(areaAgricolaDto.getEstado());

        if (areaAgricolaDto.getCidadeNome() != null && !areaAgricolaDto.getCidadeNome().isEmpty()) {
            Cidade cidade = cidadeService.buscarOuCriar(areaAgricolaDto.getCidadeNome());
            existingArea.setCidade(cidade);
        }

        String geoJsonContent = null;
        String ervaDaninhaContent = null;

        try {
            if (arquivoFazenda != null && !arquivoFazenda.isEmpty()) {
                geoJsonContent = new String(arquivoFazenda.getBytes(), StandardCharsets.UTF_8);
                Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(geoJsonContent);
                if (geometry == null) {
                    throw new RuntimeException("Geometria inválida no arquivo GeoJSON da fazenda");
                }
                existingArea.setArquivoFazenda(geometry);
            } else {
                geoJsonContent = conversorGeoJson.convertGeometryToGeoJson(existingArea.getArquivoFazenda());
            }

            if (arquivoErvaDaninha != null && !arquivoErvaDaninha.isEmpty()) {
                ervaDaninhaContent = new String(arquivoErvaDaninha.getBytes(), StandardCharsets.UTF_8);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler os arquivos enviados: " + e.getMessage());
        }

        AreaAgricola updatedArea = areaAgricolaRepository.save(existingArea);

        List<Talhao> talhoesExistentes = talhaoRepository.findByAreaAgricolaId(updatedArea.getId());
        talhoesExistentes.forEach(t -> {
            safraRepository.deleteAll(safraRepository.findByTalhaoId(t.getId()));
            talhaoRepository.flush();
        });
        talhaoRepository.deleteAll(talhoesExistentes);
        talhaoRepository.flush();

        processarTalhoesGeoJson(geoJsonContent, updatedArea, ervaDaninhaContent);

        return convertToDto(updatedArea);
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
        if (cidadeNome != null && !cidadeNome.isEmpty()) {
            Cidade cidade = cidadeService.buscarOuCriar(cidadeNome);
            areaAgricola.setCidade(cidade);
        } else {
            throw new RuntimeException("Nome da cidade não informado.");
        }

        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());

        if (dto.getStatus() != null) {
            areaAgricola.setStatus(dto.getStatus());
        } else {
            areaAgricola.setStatus(StatusArea.Pendente);
        }

        return areaAgricola;
    }

    private void processarTalhoesGeoJson(String arquivo, AreaAgricola areaAgricola, String ervaDaninha) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(arquivo);

            if (!rootNode.has("type") ||
                    !rootNode.get("type").asText().equals("FeatureCollection") ||
                    !rootNode.has("features")) {
                throw new RuntimeException("Arquivo GeoJSON inválido: deve ser um FeatureCollection");
            }

            JsonNode features = rootNode.get("features");

            Geometry geometryErvaDaninha = null;
            if (ervaDaninha != null && !ervaDaninha.trim().isEmpty()) {
                JsonNode ervaDaninhaNode = objectMapper.readTree(ervaDaninha);
                if (!ervaDaninhaNode.has("type") ||
                        !ervaDaninhaNode.get("type").asText().equals("FeatureCollection")) {
                    throw new RuntimeException("Arquivo erva daninha inválido: deve ser um FeatureCollection");
                }

                @SuppressWarnings("unused")
                GeometryFactory geometryFactory = new GeometryFactory();
                for (JsonNode feature : ervaDaninhaNode.get("features")) {
                    String geometryJson = feature.get("geometry").toString();
                    Geometry geometry = conversorGeoJson.convertGeoJsonToGeometry(geometryJson);
                    if (geometryErvaDaninha == null) {
                        geometryErvaDaninha = geometry;
                    } else {
                        geometryErvaDaninha = geometryErvaDaninha.union(geometry);
                    }
                }
            }

            for (JsonNode feature : features) {
                processarTalhaoFeature(feature, areaAgricola, geometryErvaDaninha);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar arquivo GeoJSON: " + e.getMessage(), e);
        }
    }

    private void processarFeatures(JsonNode featuresNode, AreaAgricola areaAgricola, String ervaDaninha) {
        if (featuresNode == null || !featuresNode.isArray()) {
            throw new RuntimeException("Features inválidas no GeoJSON");
        }

        Geometry geometryErvaDaninha = null;
        if (ervaDaninha != null && !ervaDaninha.trim().isEmpty()) {
            try {
                geometryErvaDaninha = conversorGeoJson.convertGeoJsonToGeometry(ervaDaninha);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter arquivo de erva daninha: " + e.getMessage());
            }
        }

        for (JsonNode feature : featuresNode) {
            processarTalhaoFeature(feature, areaAgricola, geometryErvaDaninha);
        }
    }

    @Transactional
    public void processarTalhaoFeature(JsonNode feature, AreaAgricola areaAgricola, Geometry geometryErvaDaninha) {
        JsonNode properties = feature.get("properties");

        Talhao novoTalhao = new Talhao();
        novoTalhao.setAreaAgricola(areaAgricola);
        novoTalhao.setArea(properties.get("AREA_HA_TL").asDouble());

        String nomeTipoSolo = properties.get("SOLO").asText();
        String nomeTipoSoloCap = nomeTipoSolo.substring(0, 1).toUpperCase() + nomeTipoSolo.substring(1).toLowerCase();
        TipoSolo tipoSolo = tipoSoloRepository.findByTipoSolo(nomeTipoSoloCap)
                .orElseThrow(() -> new RuntimeException("TipoSolo não encontrado: " + nomeTipoSoloCap));
        novoTalhao.setTipoSolo(tipoSolo);

        Talhao talhaoSalvo = talhaoRepository.save(novoTalhao);

        String idSafra = properties.get("MN_TL").asText();
        Safra novaSafra = new Safra();
        novaSafra.setId(idSafra);
        novaSafra.setTalhao(talhaoSalvo);
        novaSafra.setAno(Integer.parseInt(properties.get("SAFRA").asText().split("/")[0]));
        novaSafra.setStatus(StatusSafra.Pendente);

        String nomeCultura = properties.get("CULTURA").asText();
        String nomeCulturaCap = nomeCultura.substring(0, 1).toUpperCase() + nomeCultura.substring(1).toLowerCase();
        Cultura cultura = culturaRepository.findByNome(nomeCulturaCap)
                .orElseThrow(() -> new RuntimeException("Cultura não encontrada: " + nomeCulturaCap));
        novaSafra.setCultura(cultura);

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
        }

        dto.setNomeFazenda(areaAgricola.getNomeFazenda());
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