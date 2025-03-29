package fatec.porygon.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

import fatec.porygon.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody UsuarioDto usuarioDto) {
        // Adicione validações aqui, se necessário
        return ResponseEntity.ok(usuarioService.criarUsuario(usuarioDto));
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
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
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

