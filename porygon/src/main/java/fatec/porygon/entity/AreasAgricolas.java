package fatec.porygon.entity;

import fatec.porygon.enums.StatusArea;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "area_agricola")
@Data
public class AreasAgricolas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "usuario_upgrade_id")
    private Usuario usuarioUpgrade;

    @ManyToOne
    @JoinColumn(name = "usuario_aprovador_id")
    private Usuario usuarioAprovador;

    @Column(nullable = false)
    private String nomeFazenda;

    @Column(nullable = false)
    private String cultura;

    @Column(nullable = false)
    private Double produtividadeAno;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private String tipoSolo;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false, columnDefinition = "JSON")
    private String vetorRaiz;

    @Column(columnDefinition = "JSON")
    private String vetorAtualizado;

    @Column(columnDefinition = "JSON")
    private String vetorAprovado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusArea status = StatusArea.PENDENTE;
}