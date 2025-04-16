package fatec.porygon.controller;

import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.service.TalhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;

    @Autowired
    public TalhaoController(TalhaoService talhaoService) {
        this.talhaoService = talhaoService;
    }

    @PostMapping
    public ResponseEntity<TalhaoDto> criarTalhao(@RequestBody TalhaoDto talhaoDto) {
        if (talhaoDto.getArea() == null || talhaoDto.getArea() <= 0) {
            System.out.println("Erro: Área do talhão é inválida.");
            return ResponseEntity.badRequest().body(null);
        }
        if (talhaoDto.getTipoSoloId() == null) {
            System.out.println("Erro: Tipo de solo não informado.");
            return ResponseEntity.badRequest().body(null);
        }
        if (talhaoDto.getAreaAgricolaId() == null) {
            System.out.println("Erro: Área agrícola não informada.");
            return ResponseEntity.badRequest().body(null);
        }
        if (talhaoDto.getCulturaNome() == null || talhaoDto.getCulturaNome().trim().isEmpty()) {
            System.out.println("Erro: Nome da cultura não informado.");
            return ResponseEntity.badRequest().body(null);
        }

        TalhaoDto novoTalhao = talhaoService.criarTalhao(talhaoDto);
        return new ResponseEntity<>(novoTalhao, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TalhaoDto>> listarTalhoes() {
        List<TalhaoDto> talhoes = talhaoService.listarTodos();
        return ResponseEntity.ok(talhoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalhaoDto> buscarTalhaoPorId(@PathVariable Long id) {
        try {
            TalhaoDto talhao = talhaoService.buscarPorId(id);
            return ResponseEntity.ok(talhao);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TalhaoDto> atualizarTalhao(@PathVariable Long id, @RequestBody TalhaoDto talhaoDto) {
        try {
            if (talhaoDto.getArea() == null || talhaoDto.getArea() <= 0) {
                System.out.println("Erro: Área do talhão é inválida.");
                return ResponseEntity.badRequest().build();
            }
            if (talhaoDto.getTipoSoloId() == null) {
                System.out.println("Erro: Tipo de solo não informado.");
                return ResponseEntity.badRequest().build();
            }
            if (talhaoDto.getAreaAgricolaId() == null) {
                System.out.println("Erro: Área agrícola não informada.");
                return ResponseEntity.badRequest().build();
            }
            if (talhaoDto.getCulturaNome() == null || talhaoDto.getCulturaNome().trim().isEmpty()) {
                System.out.println("Erro: Nome da cultura não informado.");
                return ResponseEntity.badRequest().build();
            }

            TalhaoDto talhaoAtualizado = talhaoService.atualizarTalhao(id, talhaoDto);
            return ResponseEntity.ok(talhaoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTalhao(@PathVariable Long id) {
        try {
            talhaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}