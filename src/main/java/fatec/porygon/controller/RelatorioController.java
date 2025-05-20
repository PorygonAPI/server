package fatec.porygon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.porygon.dto.RelatorioPorAnalistaDto;
import fatec.porygon.dto.RelatorioProdutividadeDto;
import fatec.porygon.dto.StatusRelatorioDto;
import fatec.porygon.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/status")
    public StatusRelatorioDto obterStatusGeral() {
        return relatorioService.getContagemPorStatus();
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/analistas")
    public List<RelatorioPorAnalistaDto> obterRelatorioPorAnalista() {
        return relatorioService.getRelatorioPorAnalista();
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    @GetMapping("/produtividade")
    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        return relatorioService.gerarRelatorioProdutividade();
    }
}
