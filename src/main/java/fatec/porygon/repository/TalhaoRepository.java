package main.java.fatec.porygon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fatec.porygon.model.Talhao;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {

}
