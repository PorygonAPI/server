package fatec.porygon.controller;

<<<<<<< HEAD
import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
=======
import java.util.List;

>>>>>>> 5e2e92985e1bb99f456af601a6221ecee60462c7
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class SafraRelatorioController {

    @Autowired
    private SafraService safraService;

    @GetMapping("/safras-aprovadas")
    public ResponseEntity<List<SafraRelatorioDto>> listarSafrasAprovadas() {
        List<SafraRelatorioDto> relatorio = safraService.gerarRelatorioSafrasAprovadas();
        return ResponseEntity.ok(relatorio);
    }
}

=======
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
}
>>>>>>> 5e2e92985e1bb99f456af601a6221ecee60462c7
