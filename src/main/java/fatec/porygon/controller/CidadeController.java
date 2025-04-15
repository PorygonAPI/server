package fatec.porygon.controller;

import fatec.porygon.entity.Cidade;
import fatec.porygon.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listarCidades() {
        List<Cidade> cidades = cidadeService.listarTodas();
        return ResponseEntity.ok(cidades);
    }
}
