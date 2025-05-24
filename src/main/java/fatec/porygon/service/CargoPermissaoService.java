package fatec.porygon.service;

import fatec.porygon.dto.CargoPermissaoDto;
import fatec.porygon.entity.CargoPermissao;
import fatec.porygon.repository.CargoPermissaoRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoPermissaoService {

    private final CargoPermissaoRepository cargoPermissaoRepository;

    public CargoPermissaoService(CargoPermissaoRepository cargoPermissaoRepository) {
        this.cargoPermissaoRepository = cargoPermissaoRepository;
    }

    public List<CargoPermissaoDto> buscarTodos() {
        List<CargoPermissao> lista = cargoPermissaoRepository.findAll();
        return lista.stream()
                .map(cp -> new CargoPermissaoDto(cp.getCargo().getId(), cp.getPermissao().getId()))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<Long> buscarCargos() {
        return cargoPermissaoRepository.findAll().stream()
                .map(cp -> cp.getCargo().getId())
                .distinct()
                .collect(Collectors.toList());
    }
}
