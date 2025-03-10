package fatec.porygon.controller;

import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutentificacaoController {
    private final AutenticacaoService authService;

    public AutentificacaoController(AutenticacaoService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDto UsuarioDto) {
        String token = authService.autenticar(UsuarioDto);
        return ResponseEntity.ok(token);
    }
}
