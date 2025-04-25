package fatec.porygon.repository;

import fatec.porygon.entity.Safra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SafraRepository extends JpaRepository<Safra, String> {
    List<Safra> findByTalhaoId(Long talhaoId);
}