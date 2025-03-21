package fatec.porygon.controller;

import fatec.porygon.entity.Permissao;
import fatec.porygon.service.PermissaoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

    private final PermissaoService permissaoService;

    public PermissaoController(PermissaoService permissaoService){
        this.permissaoService = permissaoService;
    }

    @GetMapping
    public ResponseEntity<List<Permissao>> buscarTodos(){
        return ResponseEntity.ok(permissaoService.buscarTodos());
    }
   
}
