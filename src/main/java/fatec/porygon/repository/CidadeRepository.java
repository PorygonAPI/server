package fatec.porygon.repository;

import fatec.porygon.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);
}
