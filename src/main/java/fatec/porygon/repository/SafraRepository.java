package fatec.porygon.repository;

import fatec.porygon.dto.TalhaoResumoDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SafraRepository extends JpaRepository<Safra, String> {
    List<Safra> findByTalhaoId(Long talhaoId);

    @Query("""
        SELECT new fatec.porygon.dto.TalhaoResumoDto(
            t.id,
            a.nomeFazenda,
            cast(s.id as long),
            c.nome,
            s.ano
        )
        FROM Safra s
        JOIN s.talhao t
        JOIN t.areaAgricola a
        JOIN s.cultura c
        WHERE s.usuarioAnalista.id = :idUsuario
        AND s.status = :status
    """)
    List<TalhaoResumoDto> buscarTalhoesPorStatus(
        @Param("idUsuario") Long idUsuario, 
        @Param("status") StatusSafra status
    );

    @Query("""
        SELECT t.id, a.nomeFazenda, s.id, c.nome, s.ano
        FROM Safra s
        JOIN s.talhao t
        JOIN t.areaAgricola a
        JOIN s.cultura c
        WHERE s.usuarioAnalista.id = :idUsuario
        AND s.status = :status
    """)
    List<Object[]> buscarTalhoesBrutosPorStatus(
        @Param("idUsuario") Long idUsuario,
        @Param("status") StatusSafra status
    );

    List<Safra> findByStatus(StatusSafra statusSafra);

    long countByStatus(StatusSafra status);
}