package fatec.porygon.service;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Usuario;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaAgricolaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AreaAgricolaService(AreaAgricolaRepository areaAgricolaRepository, UsuarioRepository usuarioRepository) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.usuarioRepository = usuarioRepository;
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
        
        areaAgricola.setnome_fazenda(dto.getnome_fazenda());
        areaAgricola.setCultura(dto.getCultura());
        areaAgricola.setprodutividade_ano(dto.getprodutividade_ano());
        areaAgricola.setArea(dto.getArea());
        areaAgricola.settipo_solo(dto.gettipo_solo());
        areaAgricola.setCidade(dto.getCidade());
        areaAgricola.setEstado(dto.getEstado());
        areaAgricola.setvetor_raiz(dto.getvetor_raiz());
        areaAgricola.setvetor_atualizado(dto.getvetor_atualizado());
        areaAgricola.setvetor_aprovado(dto.getvetor_aprovado());
        
        if (dto.getStatus() != null) {
            areaAgricola.setStatus(dto.getStatus());
        } else {
            areaAgricola.setStatus(StatusArea.pendente);
        }
        
        return areaAgricola;
    }

    private AreaAgricolaDto convertToDto(AreaAgricola areaAgricola) {
        AreaAgricolaDto dto = new AreaAgricolaDto();
        dto.setId(areaAgricola.getId());
        
        if (areaAgricola.getusuario_id() != null) {
            dto.setusuario_id(areaAgricola.getusuario_id().getId());
        }
        
        if (areaAgricola.getUsuarioUpgrade() != null) {
            dto.setusuario_upgrade_id(areaAgricola.getUsuarioUpgrade().getId());
        }
        
        if (areaAgricola.getusuario_aprovador_id() != null) {
            dto.setusuario_aprovador_id(areaAgricola.getusuario_aprovador_id().getId());
        }
        
        dto.setnome_fazenda(areaAgricola.getnome_fazenda());
        dto.setCultura(areaAgricola.getCultura());
        dto.setprodutividade_ano(areaAgricola.getprodutividade_ano());
        dto.setArea(areaAgricola.getArea());
        dto.settipo_solo(areaAgricola.gettipo_solo());
        dto.setCidade(areaAgricola.getCidade());
        dto.setEstado(areaAgricola.getEstado());
        dto.setvetor_raiz(areaAgricola.getvetor_raiz());
        dto.setvetor_atualizado(areaAgricola.getvetor_atualizado());
        dto.setvetor_aprovado(areaAgricola.getvetor_aprovado());
        dto.setStatus(areaAgricola.getStatus());
        
        return dto;
    }
}
