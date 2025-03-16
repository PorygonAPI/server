package fatec.porygon.controller;

import org.springframework.web.bind.annotation.*;
import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(usuario -> {
            UsuarioDto dto = new UsuarioDto();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setCargoNome(usuario.getCargo().getNome());
            return dto;
        }).collect(Collectors.toList());
    }

}


