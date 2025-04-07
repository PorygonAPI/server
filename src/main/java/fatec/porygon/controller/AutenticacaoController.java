package fatec.porygon.controller;

import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
    private final AutenticacaoService authService;

    public AutenticacaoController(AutenticacaoService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioDto UsuarioDto) {
        Map<String, Object> response = authService.autenticar(UsuarioDto);

        return ResponseEntity.ok(response);
    }
}
