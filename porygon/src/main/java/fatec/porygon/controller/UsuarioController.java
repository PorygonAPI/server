package fatec.porygon.controller;

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
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());}

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

