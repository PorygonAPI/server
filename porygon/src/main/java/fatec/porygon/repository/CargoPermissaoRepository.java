package fatec.porygon.repository;

import fatec.porygon.entity.CargoPermissao;
import fatec.porygon.entity.CargoPermissaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoPermissaoRepository extends JpaRepository<CargoPermissao, CargoPermissaoId> {

}