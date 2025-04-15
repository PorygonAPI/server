package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_solo")
public class TipoSolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo", unique = true, nullable = false)
    private String tipo;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}
}

