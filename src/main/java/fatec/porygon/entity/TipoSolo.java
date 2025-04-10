package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_solo")
public class TipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tipo_solo;

    public String getTipoSolo() {return tipo_solo;}
    public void setTipoSolo(String tipoSolo) {this.tipo_solo = tipoSolo;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}

