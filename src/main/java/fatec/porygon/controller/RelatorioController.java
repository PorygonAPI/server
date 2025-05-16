package fatec.porygon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fatec.porygon.dto.RelatorioPorAnalistaDto;
import fatec.porygon.dto.RelatorioProdutividadeDto;
import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.dto.StatusRelatorioDto;
import fatec.porygon.service.RelatorioService;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import fatec.porygon.service.RelatorioSafraService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private SafraService safraService;
    private final RelatorioSafraService relatorioSafraService;

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService, RelatorioSafraService relatorioSafraService) {
        this.relatorioSafraService = relatorioSafraService;
        this.relatorioService = relatorioService;
    }

    @GetMapping("/status")
    public StatusRelatorioDto obterStatusGeral() {
        return relatorioService.getContagemPorStatus();
    }

    @GetMapping("/analistas")
    public List<RelatorioPorAnalistaDto> obterRelatorioPorAnalista() {
        return relatorioService.getRelatorioPorAnalista();
    }

    @GetMapping("/produtividade")
    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        return relatorioService.gerarRelatorioProdutividade();
    }

    @GetMapping("/safras-aprovadas")
    public ResponseEntity<List<SafraRelatorioDto>> listarSafrasAprovadas() {
        List<SafraRelatorioDto> relatorio = relatorioSafraService.gerarRelatorioSafrasAprovadas();
        return ResponseEntity.ok(relatorio);
    }
}
