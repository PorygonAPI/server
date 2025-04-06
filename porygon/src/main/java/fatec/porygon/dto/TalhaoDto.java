package fatec.porygon.dto;

import fatec.porygon.enums.StatusSafra;
import java.util.Map;

public class TalhaoDto {
    private Integer ano;
    private StatusSafra status;
    private Long cultura_id;
    private String cultura_nome;
    private Long talhao_id;
    private Double area;
    private Double produtividade_ano;
    private Long tipoSolo_id;
    private Long areaAgricola_id;
    private String arquivo_daninha;
    private String arquivoFinal_daninha;

    public Integer getAno() {return ano;}
    public void setAno(Integer ano) {this.ano = ano;}

    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}

    public Long getAreaAgricola_id() {return areaAgricola_id;}
    public void setAreaAgricola_id(Long areaAgricola_id) {this.areaAgricola_id = areaAgricola_id;}

    public String getArquivo_daninha() {return arquivo_daninha;}
    public void setArquivo_daninha(String arquivo_daninha) {this.arquivo_daninha = arquivo_daninha;}

    public String getArquivoFinal_daninha() {return arquivoFinal_daninha;}
    public void setArquivoFinal_daninha(String arquivoFinal_daninha) {this.arquivoFinal_daninha = arquivoFinal_daninha;}

    public Long getCultura_id() {return cultura_id;}
    public void setCultura_id(Long cultura_id) {this.cultura_id = cultura_id;}

    public String getCultura_nome() {return cultura_nome;}
    public void setCultura_nome(String cultura_nome) {this.cultura_nome = cultura_nome;}

    public Double getProdutividade_ano() {return produtividade_ano;}
    public void setProdutividade_ano(Double produtividade_ano) {this.produtividade_ano = produtividade_ano;}

    public StatusSafra getStatus() {return status;}
    public void setStatus(StatusSafra status) {this.status = status;}

    public Long getTalhao_id() {return talhao_id;}
    public void setTalhao_id(Long talhao_id) {this.talhao_id = talhao_id;}

    public Long getTipoSolo_id() {return tipoSolo_id;}
    public void setTipoSolo_id(Long tipoSolo_id) {this.tipoSolo_id = tipoSolo_id;}

}
