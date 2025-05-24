package fatec.porygon.controller;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.dto.CadastroAreaAgricolaDto;
import fatec.porygon.dto.FazendaDetalhadaDto;
import fatec.porygon.service.AreaAgricolaService;
import fatec.porygon.service.FazendaDetalhadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

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
    public ResponseEntity<AreaAgricolaDto> criarAreaAgricola(
            @RequestPart("nomeFazenda") String nomeFazenda,
            @RequestPart("estado") String estado,
            @RequestPart("cidadeNome") String cidadeNome,
            @RequestPart("arquivoFazenda") MultipartFile arquivoFazenda,
            @RequestPart(value = "arquivoErvaDaninha", required = false) MultipartFile arquivoErvaDaninha) {
        try {
            if (nomeFazenda == null || nomeFazenda.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidadeNome == null || cidadeNome.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(null);
            }

            if (cidadeNome == null || cidadeNome.trim().isEmpty()) {
                System.out.println("Erro: Nome da cidade está vazio ou nulo.");
                return ResponseEntity.badRequest().body(null);
            }

            CadastroAreaAgricolaDto dto = new CadastroAreaAgricolaDto();
            dto.setNomeFazenda(nomeFazenda);
            dto.setEstado(estado);
            dto.setCidadeNome(cidadeNome);
            dto.setArquivoFazenda(arquivoFazenda);
            dto.setArquivoErvaDaninha(arquivoErvaDaninha);

            AreaAgricolaDto novaAreaAgricola = areaAgricolaService.criarAreaAgricolaECriarSafra(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(novaAreaAgricola);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
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

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AreaAgricolaDto> atualizarAreaAgricola(
            @PathVariable Long id,
            @RequestParam("nomeFazenda") String nomeFazenda,
            @RequestParam("estado") String estado,
            @RequestParam("cidadeNome") String cidadeNome,
            @RequestPart(value = "arquivoFazenda", required = false) MultipartFile arquivoFazenda,
            @RequestPart(value = "arquivoErvaDaninha", required = false) MultipartFile arquivoErvaDaninha) {
        try {
            if (nomeFazenda == null || nomeFazenda.trim().isEmpty() ||
                    estado == null || estado.trim().isEmpty() ||
                    cidadeNome == null || cidadeNome.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(null);
            }

            AreaAgricolaDto existingArea;
            try {
                existingArea = areaAgricolaService.buscarAreaAgricolaPorId(id);
            } catch (RuntimeException e) {
                return ResponseEntity.notFound()
                        .build();
            }

            AreaAgricolaDto areaAgricolaDto = new AreaAgricolaDto();
            areaAgricolaDto.setId(id);
            areaAgricolaDto.setNomeFazenda(nomeFazenda);
            areaAgricolaDto.setEstado(estado);
            areaAgricolaDto.setCidadeNome(cidadeNome);
            areaAgricolaDto.setStatus(existingArea.getStatus());

            if (arquivoFazenda == null || arquivoFazenda.isEmpty()) {
                areaAgricolaDto.setArquivoFazenda(existingArea.getArquivoFazenda());
            }

            AreaAgricolaDto areaAgricolaAtualizada = areaAgricolaService.atualizarAreaAgricola(
                    id,
                    areaAgricolaDto,
                    arquivoFazenda,
                    arquivoErvaDaninha);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(areaAgricolaAtualizada);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(null);
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
