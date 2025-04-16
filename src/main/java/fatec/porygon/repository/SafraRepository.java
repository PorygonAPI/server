package fatec.porygon.repository;

import fatec.porygon.entity.Safra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SafraRepository extends JpaRepository<Safra, Long> {
    Optional<Safra> findByTalhaoId(Long talhaoId);
}