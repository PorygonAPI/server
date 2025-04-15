package fatec.porygon.service;

import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.entity.Usuario;
import fatec.porygon.entity.Cargo;
import fatec.porygon.repository.UsuarioRepository;
import fatec.porygon.repository.CargoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CargoRepository cargoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, CargoRepository cargoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cargoRepository = cargoRepository;
    }

    public UsuarioDto criarUsuario(UsuarioDto usuarioDto) {
        Optional<Cargo> cargoOpt = cargoRepository.findById(usuarioDto.getCargoId());
        if (cargoOpt.isEmpty()) {
            throw new RuntimeException("Cargo não encontrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setSenha(usuarioDto.getSenha()); // usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        usuario.setCargo(cargoOpt.get());
        usuario = usuarioRepository.save(usuario);
        return toDto(usuario);
    }

    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public ResponseEntity<UsuarioDto> buscarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(usuarioOpt.get()));
    }

    public ResponseEntity<UsuarioDto> atualizarUsuario(Long id, UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setNome(usuarioDto.getNome());
        usuario.setEmail(usuarioDto.getEmail());
        if (usuarioDto.getSenha() != null && !usuarioDto.getSenha().isEmpty()) {
            usuario.setSenha(usuarioDto.getSenha()); //usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        }

        Optional<Cargo> cargoOpt = cargoRepository.findById(usuarioDto.getCargoId());
        if (cargoOpt.isPresent()) {
            usuario.setCargo(cargoOpt.get());
        }
        usuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(toDto(usuario));
    }

    public ResponseEntity<Void> removerUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCargoId(usuario.getCargo().getId());
        dto.setCargoNome(usuario.getCargo().getNome());
        return dto;
    }
}
