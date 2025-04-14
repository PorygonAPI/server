package fatec.porygon.repository;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.entity.Talhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalhaoPendenteRepository extends JpaRepository<Talhao, Long> {

    @Query("SELECT new fatec.porygon.dto.TalhaoPendenteDto(t.id, a.nome, c.nome, t.produtividade_ano, t.area, ts.tipo, ci.nome, ci.estado) " +
       "FROM Talhao t " +
       "JOIN t.area a " +
       "JOIN t.tipoSolo ts " +
       "JOIN a.cidade ci " +
       "JOIN t.safra s " +
       "JOIN s.cultura c " +
       "WHERE s.status = fatec.porygon.enums.StatusSafra.Pendente " +
       "AND t.usuarioAnalistaId IS NULL")
    List<TalhaoPendenteDto> buscarTalhoesPendentes();
}

