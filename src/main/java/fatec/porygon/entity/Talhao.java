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

    @Column(name = "area")
    private Double area;

    @ManyToOne
    @JoinColumn(name = "tipo_solo_id")
    private TipoSolo tipoSolo;

    @ManyToOne
    @JoinColumn(name = "area_agricola_id")
    private AreaAgricola areaAgricola;

    @OneToMany(mappedBy = "talhao")
    private List<Safra> safras = new ArrayList<>();

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}

    public TipoSolo getTipoSolo() {return tipoSolo;}
    public void setTipoSolo(TipoSolo tipoSolo) {this.tipoSolo = tipoSolo;}

    public AreaAgricola getAreaAgricola() {return areaAgricola;}
    public void setAreaAgricola(AreaAgricola areaAgricola) {this.areaAgricola = areaAgricola;}

    public List<Safra> getSafras() {return safras;}
    public void setSafras(List<Safra> safras) {this.safras = safras;}
}

