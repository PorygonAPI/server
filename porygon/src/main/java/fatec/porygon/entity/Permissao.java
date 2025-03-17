package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permissao")
public class Permissao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tipo;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}
}