package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final CidadeRepository cidadeRepository;

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository, CidadeRepository cidadeRepository) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.cidadeRepository = cidadeRepository;
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
            Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(dto.getCidadeNome());
            Cidade cidade;
            if (cidadeOpt.isPresent()) {
                cidade = cidadeOpt.get();
            } else {
                cidade = new Cidade();
                cidade.setNome(dto.getCidadeNome());
                cidade = cidadeRepository.save(cidade);
            }
            areaAgricola.setCidadeId(cidade);
        }
        areaAgricola.setArquivoFazenda(dto.getArquivoFazenda());
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
        dto.setArquivoFazenda(areaAgricola.getArquivoFazenda());
        return dto;
    }
}
