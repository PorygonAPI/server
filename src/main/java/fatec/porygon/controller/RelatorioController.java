package fatec.porygon.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fatec.porygon.dto.RelatorioPorAnalistaDto;
import fatec.porygon.dto.RelatorioProdutividadeDto;
import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.dto.StatusRelatorioDto;
import fatec.porygon.service.RelatorioService;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;

@PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private SafraService safraService;
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService, RelatorioService RelatorioService) {
        this.relatorioService = relatorioService;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/status")
    public ResponseEntity<StatusRelatorioDto> getStatusRelatorio(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        StatusRelatorioDto dto = relatorioService.getContagemPorStatus(dataInicial, dataFinal);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/analistas")
    public ResponseEntity<List<RelatorioPorAnalistaDto>> getRelatorioAnalistas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal
    ) {
        List<RelatorioPorAnalistaDto> relatorio = relatorioService.getRelatorioPorAnalista(dataInicial, dataFinal);
        return ResponseEntity.ok(relatorio);
    }    

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/produtividade")
    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        return relatorioService.gerarRelatorioProdutividade();
    }

    @GetMapping("/safras-aprovadas")
    public ResponseEntity<Map<String, Object>> listarSafrasAprovadasComMedia() {
        List<SafraRelatorioDto> relatorio = relatorioService.gerarRelatorioSafrasAprovadas();
        List<SafraRelatorioDto.MediaAnalistaDto> medias = relatorioService.calcularMediaPorAnalista();

        Map<String, Object> resposta = Map.of(
            "safrasAprovadas", relatorio,
            "mediaPorAnalista", medias
        );

        return ResponseEntity.ok(resposta);
    }

}


