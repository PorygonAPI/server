package fatec.porygon.controller;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.service.TalhaoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;

    public TalhaoController(TalhaoService talhaoService) {
        this.talhaoService = talhaoService;
    }

    @GetMapping("/pendentes")
    public List<TalhaoPendenteDto> listarTalhoesPendentes() {
        return talhaoService.listarTalhoesPendentes();
    }

    @PutMapping("/{id}/salvar")
    public ResponseEntity<String> salvarTalhao(@PathVariable Long id) {
        try {
            talhaoService.salvarEdicaoTalhao(id);
            return ResponseEntity.ok("Talhão salvo com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Talhão não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o talhão.");
        }
    }

    @PutMapping("/{id}/aprovar")
    public ResponseEntity<String> aprovarTalhao(@PathVariable Long id) {
        try {
            talhaoService.aprovarTalhao(id);
            return ResponseEntity.ok("Talhão aprovado com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Talhão não encontrado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao aprovar o talhão.");
        }
    }
}
