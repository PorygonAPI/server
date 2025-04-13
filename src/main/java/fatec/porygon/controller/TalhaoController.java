package fatec.porygon.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fatec.porygon.service.TalhaoService;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;

    public TalhaoController(TalhaoService talhaoService) {
        this.talhaoService = talhaoService;
    }

    @PostMapping("/atribuir-analista")
    public ResponseEntity<String> atribuirAnalistaAoTalhao(@RequestParam Long talhaoId,
                                                           @RequestParam Long usuarioAnalistaId) {
        talhaoService.atribuirAnalista(talhaoId, usuarioAnalistaId);
        return ResponseEntity.ok("Analista atribuído com sucesso.");
    }
}
