package fatec.porygon.controller;

import fatec.porygon.entity.Cidade;
import fatec.porygon.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeService cidadeService;

    @Autowired
    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public ResponseEntity<Cidade> criarCidade(@RequestBody Map<String, String> payload) {
        String nome = payload.get("nome");
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        Cidade cidade = cidadeService.buscarOuCriar(nome);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> listarCidades() {
        List<Cidade> cidades = cidadeService.listarTodas();
        return ResponseEntity.ok(cidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarCidadePorId(@PathVariable Long id) {
        Optional<Cidade> cidade = cidadeService.buscarPorId(id);
        return cidade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizarCidade(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String nome = payload.get("nome");
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            Cidade cidade = cidadeService.atualizar(id, nome);
            return ResponseEntity.ok(cidade);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCidade(@PathVariable Long id) {
        try {
            cidadeService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
