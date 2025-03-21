package fatec.porygon.controller;

import fatec.porygon.entity.CargoPermissao;
import fatec.porygon.service.CargoPermissaoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargo-permissao")
public class CargoPermissaoController {

    private final CargoPermissaoService cargoPermissaoService;

    public CargoPermissaoController(CargoPermissaoService cargoPermissaoService){
        this.cargoPermissaoService = cargoPermissaoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoPermissao>> buscarTodos() {
        return ResponseEntity.ok(cargoPermissaoService.buscarTodos());
    }

}
