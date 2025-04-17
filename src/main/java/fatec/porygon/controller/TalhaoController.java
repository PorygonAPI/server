package fatec.porygon.controller;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.service.TalhaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoService talhaoService;

    public TalhaoController(TalhaoService talhaoService) {
        this.talhaoService = talhaoService;
    }

    @GetMapping("/pendentes")
    public List<TalhaoPendenteDto> listarTalhoesPendentes() {
        return talhaoService.listarTalhoesPendentes();
    }
}
