package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.entity.Usuario;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CidadeService cidadeService;

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository, 
                              UsuarioRepository usuarioRepository,
                              CidadeService cidadeService) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.usuarioRepository = usuarioRepository;
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
        
        Cidade cidade = cidadeService.buscarOuCriar(dto.getCidade());
        areaAgricola.setCidadeId(cidade);
        
        areaAgricola.setNomeFazenda(dto.getNomeFazenda());
        areaAgricola.setEstado(dto.getEstado());
        
        if (dto.getStatus() != null) {
            areaAgricola.setStatus(dto.getStatus());
        } else {
            areaAgricola.setStatus(StatusArea.Pendente);
        }
        
        return areaAgricola;
    }

    private AreaAgricolaDto convertToDto(AreaAgricola areaAgricola) {
        AreaAgricolaDto dto = new AreaAgricolaDto();
        dto.setId(areaAgricola.getId());
        
        if (areaAgricola.getCidadeId() != null) {
            dto.setCidadeNome(areaAgricola.getCidadeId().getNome());
            dto.setCidade(areaAgricola.getCidadeId().getNome());
        }
        
        dto.setNomeFazenda(areaAgricola.getNomeFazenda());
        dto.setnome_fazenda(areaAgricola.getNomeFazenda());
        dto.setEstado(areaAgricola.getEstado());
        dto.setStatus(areaAgricola.getStatus());
        
        if (areaAgricola.getArquivoFazenda() != null) {
            dto.setArquivoFazenda(areaAgricola.getArquivoFazenda().toString());
        }
        
        return dto;
    }
}
