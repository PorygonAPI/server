package fatec.porygon.controller;

import fatec.porygon.dto.CargoPermissaoDto;
import fatec.porygon.service.CargoPermissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargo-permissao")
public class CargoPermissaoController {

    private final CargoPermissaoService cargoPermissaoService;

    public CargoPermissaoController(CargoPermissaoService cargoPermissaoService) {
        this.cargoPermissaoService = cargoPermissaoService;
    }

    @GetMapping
    public ResponseEntity<List<CargoPermissaoDto>> listarTodos() {
        return ResponseEntity.ok(cargoPermissaoService.buscarTodos());
    }

    @GetMapping("/cargos")
    public ResponseEntity<List<Long>> listarCargos() {
        return ResponseEntity.ok(cargoPermissaoService.buscarCargos());
    }
}