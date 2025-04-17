package fatec.porygon.repository;

import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
    @Query("SELECT t FROM Talhao t WHERE t.areaAgricola.id = :areaAgricolaId")
    List<Talhao> findByAreaAgricolaId(@Param("areaAgricolaId") Long areaAgricolaId);
}
