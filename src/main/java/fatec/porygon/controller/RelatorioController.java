package fatec.porygon.controller;

import java.time.LocalDate;
import java.util.List;

import fatec.porygon.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fatec.porygon.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/status")
    public ResponseEntity<StatusRelatorioDto> getStatusRelatorio(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        StatusRelatorioDto dto = relatorioService.getContagemPorStatus(dataInicial, dataFinal);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/analistas")
    public ResponseEntity<List<RelatorioPorAnalistaDto>> getRelatorioAnalistas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal
    ) {
        List<RelatorioPorAnalistaDto> relatorio = relatorioService.getRelatorioPorAnalista(dataInicial, dataFinal);
        return ResponseEntity.ok(relatorio);
    }    

    @GetMapping("/produtividade")
    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        return relatorioService.gerarRelatorioProdutividade();
    }

    @GetMapping("/media/cultura")
    public ResponseEntity<List<ProdutividadeMediaPorCulturaDto>> mediaPorCultura() {
        return ResponseEntity.ok(relatorioService.mediaPorCultura());
    }

    @GetMapping("/media/estado")
    public ResponseEntity<List<ProdutividadeMediaPorEstadoDto>> mediaPorEstado() {
        return ResponseEntity.ok(relatorioService.mediaPorEstado());
    }

    @GetMapping("/media/tipo-solo")
    public ResponseEntity<List<ProdutividadeMediaPorTipoSoloDto>> mediaPorTipoSolo() {
        return ResponseEntity.ok(relatorioService.mediaPorTipoSolo());
    }
}


