package fatec.porygon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fatec.porygon.entity.Talhao;

@Repository
public interface TalhaoRepository extends JpaRepository<Talhao, Long> {

}