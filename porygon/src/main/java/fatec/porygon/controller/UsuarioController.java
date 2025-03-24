package fatec.porygon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    public List<UsuarioDto> listarUsuarios() {
        try {
            return usuarioRepository.findAll().stream().map(usuario -> {
                UsuarioDto dto = new UsuarioDto();
                dto.setId(usuario.getId());
                dto.setNome(usuario.getNome());
                dto.setEmail(usuario.getEmail());
                dto.setCargoNome(usuario.getCargo().getNome());
                return dto;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            // Handle the exception (e.g., log it, rethrow it, return a default value, etc.)
            throw new RuntimeException("Error listing users", e);
        }

    }

}


