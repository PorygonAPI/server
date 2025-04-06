package fatec.porygon.entity;

import jakarta.persistence.*;

public class tipoSolo {

    @Entity
    @Table(name = "tipo_solo")
    public class TipoSolo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String tipoSolo;
    }

}
