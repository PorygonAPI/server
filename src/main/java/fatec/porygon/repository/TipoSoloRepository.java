package main.java.fatec.porygon.repository;

import fatec.porygon.entity.TipoSolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long> {
    Optional<TipoSolo> findByTipoSolo(String tipoSolo);
}
