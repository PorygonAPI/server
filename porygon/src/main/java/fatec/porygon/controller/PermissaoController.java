package fatec.porygon.controller;

import fatec.porygon.dto.PermissaoDto;
import fatec.porygon.service.PermissaoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

    private final PermissaoService permissaoService;

    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    @GetMapping
    public ResponseEntity<List<PermissaoDto>> buscarTodos() {
        return ResponseEntity.ok(permissaoService.buscarTodos());
    }
}