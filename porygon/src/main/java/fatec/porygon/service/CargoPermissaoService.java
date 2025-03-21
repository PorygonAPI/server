package fatec.porygon.service;

import fatec.porygon.entity.CargoPermissao;
import fatec.porygon.repository.CargoPermissaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CargoPermissaoService {

    private final CargoPermissaoRepository cargoPermissaoRepository;

    public CargoPermissaoService(CargoPermissaoRepository cargoPermissaoRepository){
        this.cargoPermissaoRepository = cargoPermissaoRepository;
    }

    public List<CargoPermissao> buscarTodos() {
        return cargoPermissaoRepository.findAll();
    }
}