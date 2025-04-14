package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
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

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository,
                              CidadeService cidadeService) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.cidadeService = cidadeService;
    }

    @Transactional
    public AreaAgricolaDto criarAreaAgricola(AreaAgricolaDto areaAgricolaDto) {
        System.out.println("Recebido DTO: " + areaAgricolaDto);
        AreaAgricola areaAgricola = convertToEntity(areaAgricolaDto);
        System.out.println("Entidade convertida: " + areaAgricola);
        AreaAgricola savedAreaAgricola = areaAgricolaRepository.save(areaAgricola);
        System.out.println("Entidade salva no banco: " + savedAreaAgricola);
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
        
        Cidade cidade = cidadeService.buscarOuCriar(dto.getCidadeNome());
        areaAgricola.setCidade(cidade);
        
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
                GeoJsonReader reader = new GeoJsonReader();
                Geometry geometry = reader.read(dto.getArquivoFazenda());
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
        }
        
        dto.setNomeFazenda(areaAgricola.getNomeFazenda());
        dto.setEstado(areaAgricola.getEstado());
        dto.setStatus(areaAgricola.getStatus());
        
        // Conversão de Geometry para GeoJSON
        if (areaAgricola.getArquivoFazenda() != null) {
            GeoJsonWriter writer = new GeoJsonWriter();
            String geoJson = writer.write(areaAgricola.getArquivoFazenda());
            dto.setArquivoFazenda(geoJson);
        }
        
        return dto;
    }
}
