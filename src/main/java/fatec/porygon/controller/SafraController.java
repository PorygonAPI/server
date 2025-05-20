package fatec.porygon.controller;

import fatec.porygon.dto.AtualizarSafraRequestDto;
import fatec.porygon.dto.SafraDto;
import fatec.porygon.dto.SafraGeoJsonDto;
import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.dto.TalhaoResumoDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.service.SafraService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @PostMapping
    public ResponseEntity<Safra> criar(@RequestBody Safra safra) {
        Safra salva = safraService.salvar(safra);
        safra.setDataCadastro(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    @GetMapping
    public ResponseEntity<List<SafraDto>> listar() {
        return ResponseEntity.ok(safraService.listarTodas());
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    @GetMapping("/{id}")
    public ResponseEntity<Safra> buscar(@PathVariable String id) {
        return ResponseEntity.ok(safraService.buscarPorId(id));
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @PutMapping("/{id}")
    public ResponseEntity<Safra> atualizar(@PathVariable String id, @RequestBody Safra safra) {
        return ResponseEntity.ok(safraService.atualizar(id, safra));
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    @PutMapping("/{idSafra}/atualizar")
    public ResponseEntity<String> atualizarSafra(
            @PathVariable Long idSafra,
            @RequestBody AtualizarSafraRequestDto request) {
        safraService.atualizarSafra(String.valueOf(idSafra), request);
        return ResponseEntity.ok("Safra atualizada com sucesso.");
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        safraService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('Analista')")
    @GetMapping("/api/talhoes/usuario/{idUsuario}")
    public ResponseEntity<Map<String, List<TalhaoResumoDto>>> listarTalhoesDoUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(safraService.listarTalhoesPorUsuario(idUsuario));
    }

    @PreAuthorize("hasAuthority('Analista')")
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

    @PreAuthorize("hasAuthority('Analista')")
    @GetMapping("/pendentes")
    public List<TalhaoPendenteDto> listarSafrasPendentes() {
        return safraService.listarSafrasPendentes();
    }

    @PreAuthorize("hasAuthority('Analista')")
    @PutMapping("/{id}/salvar")
    public ResponseEntity<String> salvarSafra(
            @PathVariable String id,
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile) {
        try {
            safraService.salvarEdicaoSafra(id, geoJsonFile);
            return ResponseEntity.ok("Safra salva com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a safra: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('Analista')")
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<String> aprovarSafra(
            @PathVariable String id,
            @RequestParam(value = "geoJsonFile", required = false) MultipartFile geoJsonFile) {
        try {
            safraService.aprovarSafra(id, geoJsonFile);
            return ResponseEntity.ok("Safra aprovada com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao aprovar a safra: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    @GetMapping(value = "/{id}/vetor", produces = MediaType.MULTIPART_MIXED_VALUE)
    public ResponseEntity<MultiValueMap<String, Object>> buscarSafraGeoJson(@PathVariable String id) {
        SafraGeoJsonDto dto = safraService.buscarSafraGeoJson(id);
        Safra safra = safraService.buscarSafra(id);

        ByteArrayResource arquivoFazenda = safraService.obterArquivoFazenda(safra);
        ByteArrayResource arquivoDaninha = safraService.obterArquivoDaninha(safra);
        ByteArrayResource arquivoFinalDaninha = safraService.obterArquivoFinalDaninha(safra);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SafraGeoJsonDto> jsonPart = new HttpEntity<>(dto, jsonHeaders);
        body.add("dadosSafra", jsonPart);

        body.add("arquivoFazenda", new HttpEntity<>(arquivoFazenda, criarHeaders("arquivoFazenda.geojson")));
        body.add("arquivoDaninha", new HttpEntity<>(arquivoDaninha, criarHeaders("arquivoDaninha.geojson")));
        body.add("arquivoFinalDaninha",
                new HttpEntity<>(arquivoFinalDaninha, criarHeaders("arquivoFinalDaninha.geojson")));

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    private HttpHeaders criarHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/geo+json"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        return headers;
    }

}
