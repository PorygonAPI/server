package fatec.porygon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fatec.porygon.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import java.util.List;
import fatec.porygon.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;}

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.criarUsuario(usuarioDto));}
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

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);}

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.atualizarUsuario(id, usuarioDto);}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        return usuarioService.removerUsuario(id);
    }
}

