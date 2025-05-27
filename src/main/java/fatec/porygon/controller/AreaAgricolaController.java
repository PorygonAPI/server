package fatec.porygon.controller;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.dto.CadastroAreaAgricolaDto;
import fatec.porygon.dto.FazendaDetalhadaDto;
import fatec.porygon.service.AreaAgricolaService;
import fatec.porygon.service.FazendaDetalhadaService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/areas-agricolas")
public class AreaAgricolaController {

    private final AreaAgricolaService areaAgricolaService;
    private final FazendaDetalhadaService fazendaDetalhadaService;

    @Autowired
    public AreaAgricolaController(AreaAgricolaService areaAgricolaService,
            FazendaDetalhadaService fazendaDetalhadaService) {
        this.fazendaDetalhadaService = fazendaDetalhadaService;
        this.areaAgricolaService = areaAgricolaService;
    }

    @GetMapping("/{id}/detalhes-completos")
    public ResponseEntity<FazendaDetalhadaDto> getFazendaDetalhada(@PathVariable Long id) {
        Optional<FazendaDetalhadaDto> fazendaDetalhada = fazendaDetalhadaService.getFazendaDetalhadaById(id);
        return fazendaDetalhada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarAreaAgricola(
            @RequestPart("dados") String dadosJson,
            @RequestPart("arquivoFazenda") MultipartFile arquivoFazenda,
            @RequestPart(value = "arquivoErvaDaninha", required = false) MultipartFile arquivoErvaDaninha) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CadastroAreaAgricolaDto dto = mapper.readValue(dadosJson, CadastroAreaAgricolaDto.class);
            
            // Set the files
            dto.setArquivoFazenda(arquivoFazenda);
            dto.setArquivoErvaDaninha(arquivoErvaDaninha);

            // Validations
            if (dto.getNomeFazenda() == null || dto.getNomeFazenda().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da fazenda é obrigatório");
            }
            if (dto.getEstado() == null || dto.getEstado().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Estado é obrigatório");
            }
            if (dto.getCidadeNome() == null || dto.getCidadeNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da cidade é obrigatório");
            }

            AreaAgricolaDto novaAreaAgricola = areaAgricolaService.criarAreaAgricolaECriarSafra(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(novaAreaAgricola);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar área agrícola: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AreaAgricolaDto>> listarAreasAgricolas() {
        List<AreaAgricolaDto> areasAgricolas = areaAgricolaService.listarAreasAgricolas();
        return ResponseEntity.ok(areasAgricolas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaAgricolaDto> buscarAreaAgricolaPorId(@PathVariable Long id) {
        try {
            AreaAgricolaDto areaAgricola = areaAgricolaService.buscarAreaAgricolaPorId(id);
            return ResponseEntity.ok(areaAgricola);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarAreaAgricola(
            @PathVariable Long id,
            @RequestPart("dados") String dadosJson,
            @RequestPart(value = "arquivoFazenda", required = false) MultipartFile arquivoFazenda,
            @RequestPart(value = "arquivoErvaDaninha", required = false) MultipartFile arquivoErvaDaninha) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AreaAgricolaDto areaAgricolaDto = mapper.readValue(dadosJson, AreaAgricolaDto.class);

            if (areaAgricolaDto.getNomeFazenda() == null || areaAgricolaDto.getNomeFazenda().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da fazenda é obrigatório");
            }
            if (areaAgricolaDto.getEstado() == null || areaAgricolaDto.getEstado().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Estado é obrigatório");
            }
            if (areaAgricolaDto.getCidadeNome() == null || areaAgricolaDto.getCidadeNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da cidade é obrigatório");
            }

            if (arquivoFazenda != null && !arquivoFazenda.isEmpty()) {
                String conteudoGeoJson = new String(arquivoFazenda.getBytes(), StandardCharsets.UTF_8);
                areaAgricolaDto.setArquivoFazenda(conteudoGeoJson);
            }

            if (arquivoErvaDaninha != null && !arquivoErvaDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoErvaDaninha.getBytes(), StandardCharsets.UTF_8);
                areaAgricolaDto.setArquivoErvaDaninha(conteudoGeoJson);
            }

            AreaAgricolaDto areaAtualizada = areaAgricolaService.atualizarAreaAgricola(id, areaAgricolaDto);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(areaAtualizada);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Área agrícola não encontrada: " + e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao processar JSON: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar arquivos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAreaAgricola(@PathVariable Long id) {
        try {
            areaAgricolaService.removerAreaAgricola(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
