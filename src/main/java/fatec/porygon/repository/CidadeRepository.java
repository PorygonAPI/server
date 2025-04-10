package fatec.porygon.repository;

import fatec.porygon.entity.Cidade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Optional<Cidade> findByNome(String nome);
}
