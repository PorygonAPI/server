package fatec.porygon.controller;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.service.AreaAgricolaService;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Talhao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        if (areaAgricolaDto.getCidadeNome() == null || areaAgricolaDto.getCidadeNome().trim().isEmpty()) {
            System.out.println("Erro: Nome da cidade está vazio ou nulo.");
            return ResponseEntity.badRequest().body(null);
        }
        if (areaAgricolaDto.getArquivoFazenda() == null || areaAgricolaDto.getArquivoFazenda().trim().isEmpty()) {
            System.out.println("Erro: Arquivo Fazenda está vazio ou nulo.");
            return ResponseEntity.badRequest().body(null);
        }
        areaAgricolaDto.setStatus(StatusArea.Pendente);
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
        try {
            AreaAgricolaDto areaAgricola = areaAgricolaService.buscarAreaAgricolaPorId(id);
            return ResponseEntity.ok(areaAgricola);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaAgricolaDto> atualizarAreaAgricola(@PathVariable Long id, 
                                                               @RequestBody AreaAgricolaDto areaAgricolaDto) {
        try {
            if (areaAgricolaDto.getCidadeNome() == null || areaAgricolaDto.getCidadeNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            AreaAgricolaDto areaAgricolaAtualizada = areaAgricolaService.atualizarAreaAgricola(id, areaAgricolaDto);
            return ResponseEntity.ok(areaAgricolaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAreaAgricola(@PathVariable Long id) {
        try {
            areaAgricolaService.removerAreaAgricola(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/talhoes/processar")
    public ResponseEntity<String> processarTalhoes(
            @PathVariable Long id,
            @RequestParam("arquivo") MultipartFile arquivo) {
        try {
            // Buscar área agrícola existente
            AreaAgricolaDto areaAgricolaDto = areaAgricolaService.buscarAreaAgricolaPorId(id);
            
            // Converter DTO para entidade
            AreaAgricola areaAgricola = new AreaAgricola();
            areaAgricola.setId(areaAgricolaDto.getId());
            areaAgricola.setNomeFazenda(areaAgricolaDto.getNomeFazenda());
            areaAgricola.setEstado(areaAgricolaDto.getEstado());
            areaAgricola.setStatus(areaAgricolaDto.getStatus());
            
            // Criar novo talhão
            Talhao talhao = new Talhao();
            talhao.setArea(0.0); // Será calculado pelo service
            talhao.setAreaAgricola(areaAgricola);
            
            // Processar o arquivo GeoJSON
            areaAgricolaService.processarTalhoesGeoJson(arquivo, areaAgricola);
            return ResponseEntity.ok("Talhões processados com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao processar talhões: " + e.getMessage());
        }
    }

}
