package fatec.porygon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fatec.porygon.service.TalhaoService;
import fatec.porygon.entity.Talhao;
import fatec.porygon.dto.TalhaoDto;

import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    @Autowired
    private TalhaoService talhaoService;

    @PostMapping
    public ResponseEntity<Talhao> criarTalhao(@RequestBody TalhaoDto dto) {
        Talhao talhao = talhaoService.criarTalhao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(talhao);
    }

    @GetMapping
    public List<Talhao> listarTalhoes() {
        return talhaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talhao> buscarPorId(@PathVariable Long id) {
        try {
            Talhao talhao = talhaoService.buscarPorId(id);
            return ResponseEntity.ok(talhao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talhao> atualizarTalhao(@PathVariable Long id, @RequestBody TalhaoDto dto) {
        Talhao talhaoAtualizado = talhaoService.atualizarTalhao(id, dto);
        return ResponseEntity.ok(talhaoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        talhaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}