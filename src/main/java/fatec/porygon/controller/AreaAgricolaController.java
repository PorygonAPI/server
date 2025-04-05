package fatec.porygon.controller;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.service.AreaAgricolaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas-agricolas")
public class AreaAgricolaController {

    private final AreaAgricolaService areaAgricolaService;

    @Autowired
    public AreaAgricolaController(AreaAgricolaService areaAgricolaService) {
        this.areaAgricolaService = areaAgricolaService;
    }

    @PostMapping
    public ResponseEntity<AreaAgricolaDto> criarAreaAgricola(@RequestBody AreaAgricolaDto areaAgricolaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        areaAgricolaDto.setusuario_id(Long.valueOf(authentication.getName()));
        areaAgricolaDto.setStatus(StatusArea.pendente);
        AreaAgricolaDto novaAreaAgricola = areaAgricolaService.criarAreaAgricola(areaAgricolaDto);
        return new ResponseEntity<>(novaAreaAgricola, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AreaAgricolaDto>> listarAreasAgricolas() {
        List<AreaAgricolaDto> areasAgricolas = areaAgricolaService.listarAreasAgricolas();
        return ResponseEntity.ok(areasAgricolas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaAgricolaDto> buscarAreaAgricolaPorId(@PathVariable Long id) {
        AreaAgricolaDto areaAgricola = areaAgricolaService.buscarAreaAgricolaPorId(id);
        return ResponseEntity.ok(areaAgricola);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaAgricolaDto> atualizarAreaAgricola(@PathVariable Long id, 
                                                               @RequestBody AreaAgricolaDto areaAgricolaDto) {
        AreaAgricolaDto areaAgricolaAtualizada = areaAgricolaService.atualizarAreaAgricola(id, areaAgricolaDto);
        return ResponseEntity.ok(areaAgricolaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAreaAgricola(@PathVariable Long id) {
        areaAgricolaService.removerAreaAgricola(id);
        return ResponseEntity.noContent().build();
    }
}
