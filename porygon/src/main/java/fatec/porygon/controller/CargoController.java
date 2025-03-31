package fatec.porygon.controller;

import org.springframework.web.bind.annotation.*;
import fatec.porygon.dto.CargoDto;
import fatec.porygon.repository.CargoRepository;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }


    @GetMapping
    public List<CargoDto> listarCargos() {
        return cargoRepository.findAll().stream().map(cargo -> {
            CargoDto dto = new CargoDto();
            dto.setId(cargo.getId());
            dto.setNome(cargo.getNome());
            return dto;
        }).collect(Collectors.toList());
    }
}


