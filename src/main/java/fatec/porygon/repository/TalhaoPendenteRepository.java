package fatec.porygon.repository;

import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalhaoPendenteRepository extends JpaRepository<Talhao, Long> {

    @Query("SELECT t FROM Talhao t " +
           "JOIN FETCH t.area_agricola_id a " +
           "JOIN FETCH t.tipo_solo ts " +
           "JOIN FETCH a.cidade_id c " +
           "WHERE t.status = 'Pendente' AND t.usuarioAnalista IS NULL")
    List<Talhao> buscarTalhoesPendentes();
}

