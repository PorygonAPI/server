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
    }


