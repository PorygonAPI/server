package fatec.porygon.controller;

import fatec.porygon.dto.AreaAgricolaDto;
import fatec.porygon.dto.CadastroAreaAgricolaDto;
import fatec.porygon.dto.FazendaDetalhadaDto;
import fatec.porygon.enums.StatusArea;
import fatec.porygon.service.AreaAgricolaService;
import fatec.porygon.service.FazendaDetalhadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/areas-agricolas")
public class AreaAgricolaController {

    private final AreaAgricolaService areaAgricolaService;
    private final FazendaDetalhadaService fazendaDetalhadaService;

    @Autowired
    public AreaAgricolaController(AreaAgricolaService areaAgricolaService,
                                  FazendaDetalhadaService fazendaDetalhadaService) {
        this.fazendaDetalhadaService = fazendaDetalhadaService;
        this.areaAgricolaService = areaAgricolaService;
    }

    @GetMapping("/{id}/detalhes-completos")
    public ResponseEntity<FazendaDetalhadaDto> getFazendaDetalhada(@PathVariable Long id) {
        Optional<FazendaDetalhadaDto> fazendaDetalhada = fazendaDetalhadaService.getFazendaDetalhadaById(id);

        return fazendaDetalhada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AreaAgricolaDto> criarAreaAgricola(@RequestBody CadastroAreaAgricolaDto dto) {
        if (dto.getCidadeNome() == null || dto.getCidadeNome().trim().isEmpty()) {
            System.out.println("Erro: Nome da cidade está vazio ou nulo.");
            return ResponseEntity.badRequest().body(null);
        }
        if (dto.getArquivoFazenda() == null || dto.getArquivoFazenda().trim().isEmpty()) {
            System.out.println("Erro: Arquivo Fazenda está vazio ou nulo.");
            return ResponseEntity.badRequest().body(null);
        }
        
        AreaAgricolaDto novaAreaAgricola = areaAgricolaService.criarAreaAgricolaECriarSafra(dto);
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
}
