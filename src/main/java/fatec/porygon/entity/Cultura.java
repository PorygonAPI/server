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

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @OneToMany(mappedBy = "cultura")
    private List<Safra> safraList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Safra> getSafraList() {
        return safraList;
    }

    public void setSafraList(List<Safra> safraList) {
        this.safraList = safraList;
    }
}
