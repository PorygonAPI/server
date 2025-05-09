package fatec.porygon.controller;

import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.service.SafraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

