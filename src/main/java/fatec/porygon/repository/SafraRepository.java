package fatec.porygon.repository;

import fatec.porygon.entity.Safra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafraRepository extends JpaRepository<Safra, Long> {
    // Aqui você pode adicionar consultas personalizadas, se necessário
}
