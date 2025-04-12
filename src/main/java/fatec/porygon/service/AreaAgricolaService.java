package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.WKTWriter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository) {
        this.areaAgricolaRepository = areaAgricolaRepository;
    }

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

    public AreaAgricolaDto atualizarAreaAgricola(Long id, AreaAgricolaDto areaAgricolaDto) {
        if (!areaAgricolaRepository.existsById(id)) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
        }
        areaAgricolaDto.setId(id);
        AreaAgricola areaAgricola = convertToEntity(areaAgricolaDto);
        AreaAgricola updatedAreaAgricola = areaAgricolaRepository.save(areaAgricola);
        return convertToDto(updatedAreaAgricola);
    }

    public void removerAreaAgricola(Long id) {
        if (!areaAgricolaRepository.existsById(id)) {
            throw new RuntimeException("Área agrícola não encontrada com ID: " + id);
        }
        areaAgricolaRepository.deleteById(id);
    }

    private AreaAgricola convertToEntity(AreaAgricolaDto dto) {
        AreaAgricola areaAgricola = new AreaAgricola();
        areaAgricola.setId(dto.getId());
        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());

        if (dto.getStatus() != null) {
            areaAgricola.setStatus(dto.getStatus());
        } else {
            areaAgricola.setStatus(StatusArea.Pendente);
        }

        if (dto.getCidadeNome() != null) {
            Cidade cidade = new Cidade(dto.getCidadeNome());
            areaAgricola.setCidadeId(cidade);
        } else {
            throw new RuntimeException("O nome da cidade é obrigatório.");
        }

        if (dto.getArquivoFazenda() != null) {
            areaAgricola.setArquivoFazenda(convertStringToGeometry(dto.getArquivoFazenda()));
        }

        return areaAgricola;
    }

    private AreaAgricolaDto convertToDto(AreaAgricola areaAgricola) {
        AreaAgricolaDto dto = new AreaAgricolaDto();
        dto.setId(areaAgricola.getId());
        dto.setNomeFazenda(areaAgricola.getNomeFazenda());
        dto.setEstado(areaAgricola.getEstado());
        dto.setStatus(areaAgricola.getStatus());

        if (areaAgricola.getCidadeId() != null) {
            dto.setCidadeNome(areaAgricola.getCidadeId().getNome());
        }

        if (areaAgricola.getArquivoFazenda() != null) {
            dto.setArquivoFazenda(convertGeometryToString(areaAgricola.getArquivoFazenda()));
        }

        return dto;
    }

    private Geometry convertStringToGeometry(String geoJson) {
        try {
            System.out.println("Convertendo String para Geometry: " + geoJson);
            WKTReader reader = new WKTReader();
            return reader.read(geoJson);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter GeoJSON para Geometry: " + e.getMessage());
        }
    }
    
    private String convertGeometryToString(Geometry geometry) {
        try {
            System.out.println("Convertendo Geometry para String: " + geometry);
            WKTWriter writer = new WKTWriter();
            return writer.write(geometry);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter Geometry para GeoJSON: " + e.getMessage());
        }
    }
}