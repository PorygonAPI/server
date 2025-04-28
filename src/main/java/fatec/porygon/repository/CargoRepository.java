package fatec.porygon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fatec.porygon.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
}

