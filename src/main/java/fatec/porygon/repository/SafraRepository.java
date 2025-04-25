package fatec.porygon.repository;

import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SafraRepository extends JpaRepository<Safra, Long> {
    List<Safra> findByTalhaoId(Long talhaoId);

    @Query("""
    SELECT new fatec.porygon.dto.TalhaoResumoDTO(
        t.id, a.nomeFazenda, s.id, c.nome
    )
    FROM Safra s
    JOIN s.talhao t
    JOIN t.areaAgricola a
    JOIN s.cultura c
    WHERE s.usuarioAnalista.id = :idUsuario
    AND s.status = :status
""")
List<TalhaoResumoDTO> buscarTalhoesPorStatus(@Param("idUsuario") Long idUsuario, @Param("status") StatusSafra status);

}