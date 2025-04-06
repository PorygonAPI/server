package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_solo")
public class tipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tipoSolo;
    }

