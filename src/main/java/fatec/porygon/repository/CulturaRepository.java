package main.java.fatec.porygon.repository;

import fatec.porygon.entity.Cultura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface CulturaRepository extends JpaRepository<Cultura, Long> {
    Optional<Cultura> findByNome(String nome);
}

