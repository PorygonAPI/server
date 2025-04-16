package fatec.porygon.dto;

import fatec.porygon.enums.StatusSafra;

public class TalhaoDto {
    private Long id;
    private Double area;
    private Long areaAgricola;
    private Long tipoSolo;
    private Integer ano;
    private Float produtividadeAno;
    private String arquivoDaninha;
    private String arquivoFinalDaninha;
    private StatusSafra status;
    private Long cultura;
    private String culturaNome;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}

    public Long getAreaAgricola() {return areaAgricola;}
    public void setAreaAgricola(Long areaAgricola) {this.areaAgricola = areaAgricola;}

    public Long getTipoSolo() {return tipoSolo;}
    public void setTipoSolo(Long tipoSolo) {this.tipoSolo = tipoSolo;}

    public Integer getAno() {return ano;}
    public void setAno(Integer ano) {this.ano = ano;}

    public Float getProdutividadeAno() {return produtividadeAno;}
    public void setProdutividadeAno(Float produtividadeAno) {this.produtividadeAno = produtividadeAno;}

    public String getArquivoDaninha() {return arquivoDaninha;}
    public void setArquivoDaninha(String arquivoDaninha) {this.arquivoDaninha = arquivoDaninha;}

    public String getArquivoFinalDaninha() {return arquivoFinalDaninha;}
    public void setArquivoFinalDaninha(String arquivoFinalDaninha) {this.arquivoFinalDaninha = arquivoFinalDaninha;}

    public StatusSafra getStatus() {return status;}
    public void setStatus(StatusSafra status) {this.status = status;}

    public Long getCultura() {return cultura;}
    public void setCultura (Long cultura) {this.cultura = cultura;}

    public String getCulturaNome() {return culturaNome;}
    public void setCulturaNome(String culturaNome) {this.culturaNome = culturaNome;}
}
