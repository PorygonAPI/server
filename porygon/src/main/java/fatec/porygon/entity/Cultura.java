package fatec.porygon.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cultura")
public class Cultura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cultura")
    private List<Safra> safras = new ArrayList<>();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public List<Safra> getSafras() {return safras;}
    public void setSafras(List<Safra> safras) {this.safras = safras;}

}


