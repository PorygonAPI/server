package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_solo")
public class TipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tipoSolo;

    public String getTipoSolo() {return tipoSolo;}
    public void setTipoSolo(String tipoSolo) {this.tipoSolo = tipoSolo;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}

