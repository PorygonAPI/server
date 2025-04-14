package fatec.porygon.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "talhao")
public class Talhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "json", name = "produtividade_ano")
    private Double produtividade_ano;

    @Column(name = "area", nullable = false)
    private Double area;

    @ManyToOne
    @JoinColumn(name = "tipo_solo_id")
    private TipoSolo tipo_solo;

    @ManyToOne
    @JoinColumn(name = "area_agricola_id")
    private AreaAgricola area_agricola_id;

    @OneToMany(mappedBy = "talhao")
    private List<Safra> safras = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getProdutividade_ano() {
        return produtividade_ano;
    }

    public void setProdutividade_ano(Double produtividade_ano) {
        this.produtividade_ano = produtividade_ano;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public TipoSolo getTipo_solo() {
        return tipo_solo;
    }

    public void setTipo_solo(TipoSolo tipo_solo) {
        this.tipo_solo = tipo_solo;
    }

    public AreaAgricola getArea_agricola_id() {
        return area_agricola_id;
    }

    public void setArea_agricola_id(AreaAgricola area_agricola_id) {
        this.area_agricola_id = area_agricola_id;
    }

    public List<Safra> getSafras() {
        return safras;
    }

    public void setSafras(List<Safra> safras) {
        this.safras = safras;
    }
}

