package main.java.fatec.porygon.repository;

import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {

}