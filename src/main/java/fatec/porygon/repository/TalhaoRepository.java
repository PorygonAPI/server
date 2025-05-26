package fatec.porygon.repository;

import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import fatec.porygon.enums.StatusSafra;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
    @Query("SELECT t FROM Talhao t WHERE t.areaAgricola.id = :areaAgricolaId")
    List<Talhao> findByAreaAgricolaId(@Param("areaAgricolaId") Long areaAgricolaId);
    List<Talhao> findDistinctBySafrasStatusAndSafrasUsuarioAnalistaIsNull(StatusSafra status);

   @Query("""
    SELECT DISTINCT t FROM Talhao t
    LEFT JOIN FETCH t.safras s
    LEFT JOIN FETCH t.areaAgricola a
    LEFT JOIN FETCH a.cidade c
    LEFT JOIN FETCH t.tipoSolo ts
    LEFT JOIN FETCH s.cultura cu
    WHERE s.status = :status AND s.usuarioAnalista IS NULL
""")
List<Talhao> findAllWithPendingSafras(@Param("status") StatusSafra status);
}

