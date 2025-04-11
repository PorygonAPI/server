package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    public Long getId() {return id;}
    public String getNome() {return nome;}

    public void setId(Long id) {this.id = id;}
    public void setNome(String nome) {this.nome = nome;}
}