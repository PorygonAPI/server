package fatec.porygon.controller;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.service.TalhaoPendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talhoes")
public class TalhaoPendenteController {

    @Autowired
    private TalhaoPendenteService talhaoPendenteService;

    @GetMapping("/pendentes")
    public ResponseEntity<List<TalhaoPendenteDto>> listarTalhoesPendentes() {
        List<TalhaoPendenteDto> talhoes = talhaoPendenteService.listarTalhoesPendentes();
        return ResponseEntity.ok(talhoes);
    }
}                   
