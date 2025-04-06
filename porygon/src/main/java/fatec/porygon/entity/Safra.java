package fatec.porygon.entity;

import fatec.porygon.enums.StatusSafra;
import jakarta.persistence.*;

@Entity
public class Safra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;

    @Enumerated(EnumType.STRING)
    private StatusSafra status;
}
