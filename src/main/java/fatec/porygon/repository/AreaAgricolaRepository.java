package fatec.porygon.repository;

import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Cidade;
import fatec.porygon.enums.StatusArea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AreaAgricolaRepository extends JpaRepository<AreaAgricola, Long> {
    List<AreaAgricola> findByCidade_id(Cidade cidade);
    List<AreaAgricola> findByStatus(StatusArea status);
    List<AreaAgricola> findByCidade_idAndStatus(Cidade cidade, StatusArea status);
}
