package fatec.porygon.repository;

import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {
    Optional<Talhao> findByMnTlAndAreaAgricola(String mnTl, AreaAgricola areaAgricola);
}
