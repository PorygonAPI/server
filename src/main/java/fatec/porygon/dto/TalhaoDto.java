package fatec.porygon.dto;

import fatec.porygon.enums.StatusSafra;
import java.util.Map;
import org.locationtech.jts.geom.Geometry;

public class TalhaoDto {
    private Integer ano;
    private StatusSafra status;
    private Long cultura_id;
    private String cultura_nome;
    private Long talhao_id;
    private Double area;
    private Float produtividade_ano;
    private Long tipo_solo_id;
    private Long area_agricola_id;
    private Geometry arquivo_daninha;
    private Geometry arquivo_final_daninha;

    public Geometry getArquivo_daninha() {return arquivo_daninha;}
    public void setArquivo_daninha(Geometry arquivo_daninha) {this.arquivo_daninha = arquivo_daninha;}

    public Geometry getArquivo_final_daninha() {return arquivo_final_daninha;}
    public void setArquivo_final_daninha(Geometry arquivo_final_daninha) {this.arquivo_final_daninha = arquivo_final_daninha;}

    public Integer getAno() {return ano;}
    public void setAno(Integer ano) {this.ano = ano;}

    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}

    public Long getArea_agricola_id() {return area_agricola_id;}
    public void setArea_agricola_id(Long area_agricola_id) {this.area_agricola_id = area_agricola_id;}

    public Long getCultura_id() {return cultura_id;}
    public void setCultura_id(Long cultura_id) {this.cultura_id = cultura_id;}

    public String getCultura_nome() {return cultura_nome;}
    public void setCultura_nome(String cultura_nome) {this.cultura_nome = cultura_nome;}

    public Float getProdutividade_ano() {return produtividade_ano;}
    public void setProdutividade_ano(Float produtividade_ano) {this.produtividade_ano = produtividade_ano;}

    public StatusSafra getStatus() {return status;}
    public void setStatus(StatusSafra status) {this.status = status;}

    public Long getTalhao_id() {return talhao_id;}
    public void setTalhao_id(Long talhao_id) {this.talhao_id = talhao_id;}

    public Long getTipo_solo_id() {return tipo_solo_id;}
    public void setTipo_solo_id(Long tipo_solo_id) {this.tipo_solo_id = tipo_solo_id;}

}
