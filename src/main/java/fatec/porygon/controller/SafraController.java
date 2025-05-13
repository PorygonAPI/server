package fatec.porygon.controller;

import fatec.porygon.dto.AtualizarSafraRequestDto;
import fatec.porygon.dto.SafraDto;
import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.dto.TalhaoResumoDto;
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
    public ResponseEntity<Safra> buscar(@PathVariable String id) {
        return ResponseEntity.ok(safraService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Safra> atualizar(@PathVariable String id, @RequestBody Safra safra) {
        return ResponseEntity.ok(safraService.atualizar(id, safra));
    }

    @PutMapping("/{idSafra}/atualizar")
    public ResponseEntity<String> atualizarSafra(
            @PathVariable Long idSafra,
            @RequestBody AtualizarSafraRequestDto request) {
        safraService.atualizarSafra(String.valueOf(idSafra), request);
        return ResponseEntity.ok("Safra atualizada com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        safraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/talhoes/usuario/{idUsuario}")
    public ResponseEntity<Map<String, List<TalhaoResumoDto>>> listarTalhoesDoUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(safraService.listarTalhoesPorUsuario(idUsuario));
    }

    @PutMapping("/{safraId}/associar-analista/{usuarioId}")
    public ResponseEntity<String> associarAnalista(
            @PathVariable String safraId,
            @PathVariable Long usuarioId) {
        try {
            safraService.associarAnalista(safraId, usuarioId);
            return ResponseEntity.ok("Analista associado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao associar analista: " + e.getMessage());
        }
    }

    @GetMapping("/pendentes")
    public List<TalhaoPendenteDto> listarSafrasPendentes() {
        return safraService.listarSafrasPendentes();
    }
}
