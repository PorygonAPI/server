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

        @Column(columnDefinition = "json")
        private Double produtividade_ano;

        private Double area;

        @ManyToOne
        @JoinColumn(name = "tipo_solo_id")
        private TipoSolo tipo_solo;

        @ManyToOne
        @JoinColumn(name = "area_agricola_id")
        private AreaAgricola area_agricola_id;

        @Column(columnDefinition = "geometry")
        private String arquivo_daninha;

        @Column(columnDefinition = "geometry")
        private String arquivo_final_daninha;

        @OneToMany(mappedBy = "talhao")
        private List<Safra> safras = new ArrayList<>();

    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}

    public AreaAgricola getArea_agricola_id() {return area_agricola_id;}
    public void setArea_agricola_id(AreaAgricola area_agricola_id) {this.area_agricola_id = area_agricola_id;}

    public String getArquivo_Daninha() {return arquivo_daninha;}
    public void setArquivo_Daninha(String arquivo_daninha) {this.arquivo_daninha = arquivo_daninha;}

    public String getArquivo_Final_Daninha() {return arquivo_final_daninha;}
    public void setArquivo_Final_Daninha(String arquivo_final_daninha) {this.arquivo_final_daninha = arquivo_final_daninha;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Double getProdutividade_ano() {return produtividade_ano;}
    public void setProdutividade_ano(Double produtividade_ano) {this.produtividade_ano = produtividade_ano;}

    public List<Safra> getSafras() {return safras;}
    public void setSafras(List<Safra> safras) {this.safras = safras;}

    public TipoSolo getTipo_solo() {return tipo_solo;}
    public void setTipo_solo(TipoSolo tipo_solo) {this.tipo_solo = tipo_solo;}
}

