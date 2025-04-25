package fatec.porygon.controller;

import fatec.porygon.dto.SafraDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/safras")
public class SafraController {

    private final SafraService safraService;

    @Autowired
    public SafraController(SafraService safraService) {
        this.safraService = safraService;
    }

    @PostMapping
    public ResponseEntity<Safra> criar(@RequestBody Safra safra) {
        Safra salva = safraService.salvar(safra);
        safra.setDataCadastro(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @GetMapping
    public ResponseEntity<List<SafraDto>> listar() {
        return ResponseEntity.ok(safraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Safra> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(safraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Safra> atualizar(@PathVariable Long id, @RequestBody Safra safra) {
        return ResponseEntity.ok(safraService.atualizar(id, safra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        safraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/talhoes/usuario/{idUsuario}")
    public ResponseEntity<Map<String, List<TalhaoResumoDTO>>> listarTalhoesDoUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(safraService.listarTalhoesPorUsuario(idUsuario));
    }

    @PutMapping("/{safraId}/associar-analista/{usuarioId}")
    public ResponseEntity<Safra> associarAnalista(
            @PathVariable Long safraId,
            @PathVariable Long usuarioId) {
        Safra atualizada = safraService.associarAnalista(safraId, usuarioId);
        return ResponseEntity.ok(atualizada);
    }
}