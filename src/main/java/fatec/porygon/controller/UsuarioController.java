package fatec.porygon.controller;

import org.springframework.web.bind.annotation.*;
import fatec.porygon.dto.UsuarioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import fatec.porygon.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;}

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.criarUsuario(usuarioDto));}
    
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());}

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> buscarUsuario(@PathVariable Long id) {
        return usuarioService.buscarUsuario(id);}

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        return usuarioService.atualizarUsuario(id, usuarioDto);}

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        return usuarioService.removerUsuario(id);
    }
}

