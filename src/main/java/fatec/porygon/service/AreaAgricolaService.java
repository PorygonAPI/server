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
        
        if (dto.getusuario_id() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getusuario_id())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + dto.getusuario_id()));
            areaAgricola.setUsuario(usuario);
        }
        
        if (dto.getusuario_upgrade_id() != null) {
            Usuario usuarioUpgrade = usuarioRepository.findById(dto.getusuario_upgrade_id())
                    .orElseThrow(() -> new RuntimeException("Usuário de upgrade não encontrado com ID: " + dto.getusuario_upgrade_id()));
            areaAgricola.setUsuarioUpgrade(usuarioUpgrade);
        }
        
        if (dto.getusuario_aprovador_id() != null) {
            Usuario usuarioAprovador = usuarioRepository.findById(dto.getusuario_aprovador_id())
                    .orElseThrow(() -> new RuntimeException("Usuário aprovador não encontrado com ID: " + dto.getusuario_aprovador_id()));
            areaAgricola.setUsuarioAprovador(usuarioAprovador);
        }
        
        areaAgricola.setNomeFazenda(dto.getnome_fazenda());
        areaAgricola.setCultura(dto.getCultura());
        areaAgricola.setprodutividade_ano(dto.getprodutividade_ano());
        areaAgricola.setArea(dto.getArea());
        areaAgricola.settipo_solo(dto.gettipo_solo());
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
            dto.setCidade(areaAgricola.getCidadeId().getNome());
        }
        
        if (areaAgricola.getusuario_id() != null) {
            dto.setusuario_id(areaAgricola.getusuario_id().getId());
        }
        
        if (areaAgricola.getUsuarioUpgrade() != null) {
            dto.setusuario_upgrade_id(areaAgricola.getUsuarioUpgrade().getId());
        }
        
        if (areaAgricola.getusuario_aprovador_id() != null) {
            dto.setusuario_aprovador_id(areaAgricola.getusuario_aprovador_id().getId());
        }
        
        dto.setnome_fazenda(areaAgricola.getNomeFazenda());
        dto.setCultura(areaAgricola.getCultura());
        dto.setprodutividade_ano(areaAgricola.getprodutividade_ano());
        dto.setArea(areaAgricola.getArea());
        dto.settipo_solo(areaAgricola.gettipo_solo());
        dto.setEstado(areaAgricola.getEstado());
        dto.setStatus(areaAgricola.getStatus());
        
        return dto;
    }
}
