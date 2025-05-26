package fatec.porygon.controller;

import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.service.TalhaoService;
import jakarta.persistence.EntityNotFoundException;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;
    private final SafraService safraService;

    @Autowired
    public TalhaoController(TalhaoService talhaoService, SafraService safraService) {
        this.talhaoService = talhaoService;
        this.safraService = safraService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TalhaoDto> criarTalhao(
            @RequestPart("dados") String dadosJson,
            @RequestPart(value = "arquivoDaninha", required = false) MultipartFile arquivoDaninha) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TalhaoDto talhaoDto = mapper.readValue(dadosJson, TalhaoDto.class);

            if (talhaoDto.getArea() == null || talhaoDto.getArea() <= 0) {
                return ResponseEntity.badRequest()
                        .body(null);
            }
            if (talhaoDto.getTipoSoloNome() == null) {
                return ResponseEntity.badRequest()
                        .body(null);
            }
            if (talhaoDto.getAreaAgricola() == null) {
                return ResponseEntity.badRequest()
                        .body(null);
            }
            if (talhaoDto.getCulturaNome() == null || talhaoDto.getCulturaNome().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(null);
            }

            TalhaoDto novoTalhao = talhaoService.criarTalhao(talhaoDto, arquivoDaninha);
            return new ResponseEntity<>(novoTalhao, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarTalhao(
            @PathVariable Long id,
            @RequestPart("dados") String dadosJson,
            @RequestPart(value = "arquivoDaninha", required = false) MultipartFile arquivoDaninha) {
        try {
        ObjectMapper mapper = new ObjectMapper();
        TalhaoDto talhaoDto = mapper.readValue(dadosJson, TalhaoDto.class);

        // Existing validations
        if (talhaoDto.getArea() == null || talhaoDto.getArea() <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Área inválida"));
        }
        if (talhaoDto.getTipoSoloNome() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Tipo de solo é obrigatório"));
        }
        if (talhaoDto.getAreaAgricola() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Área agrícola é obrigatória"));
        }
        if (talhaoDto.getCulturaNome() == null || talhaoDto.getCulturaNome().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Cultura é obrigatória"));
        }
        // Add productivity validation
        if (talhaoDto.getProdutividadeAno() == null) {
            talhaoDto.setProdutividadeAno(0.0f); // Default value if not provided
        } else if (talhaoDto.getProdutividadeAno() < 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorResponse("Produtividade não pode ser negativa"));
        }

            TalhaoDto talhaoAtualizado = talhaoService.atualizarTalhao(id, talhaoDto, arquivoDaninha);
            return ResponseEntity.ok(talhaoAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Talhão não encontrado: " + e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error updating talhao " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erro ao atualizar talhão: " + e.getMessage()));
        }
    }

    class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
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
