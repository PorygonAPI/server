package fatec.porygon.repository;

import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fatec.porygon.enums.StatusSafra;

import java.util.List;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
    List<Talhao> findDistinctBySafrasStatusAndSafrasUsuarioAnalistaIsNull(StatusSafra status);
}

