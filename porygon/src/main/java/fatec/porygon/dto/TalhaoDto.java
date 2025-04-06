package fatec.porygon.dto;

import fatec.porygon.enums.StatusSafra;
import java.util.Map;

public class TalhaoDto {
    private Integer ano;
    private StatusSafra status;

    public Integer getAno() {return ano;}
    public void setAno(Integer ano) {this.ano = ano;}
    public Double getArea() {return area;}
    public void setArea(Double area) {this.area = area;}
    public Long getAreaAgricolaId() {return areaAgricolaId;}
    public void setAreaAgricolaId(Long areaAgricolaId) {this.areaAgricolaId = areaAgricolaId;}
    public String getArquivoDaninha() {return arquivoDaninha;}
    public void setArquivoDaninha(String arquivoDaninha) {this.arquivoDaninha = arquivoDaninha;}
    public String getArquivoFinalDaninha() {return arquivoFinalDaninha;}
    public void setArquivoFinalDaninha(String arquivoFinalDaninha) {this.arquivoFinalDaninha = arquivoFinalDaninha;}
    public Long getCulturaId() {return culturaId;}
    public void setCulturaId(Long culturaId) {this.culturaId = culturaId;}
    public String getCulturaNome() {return culturaNome;}
    public void setCulturaNome(String culturaNome) {this.culturaNome = culturaNome;}
    public Map<String, Float> getProdutividadeAno() {return produtividadeAno;}
    public void setProdutividadeAno(Map<String, Float> produtividadeAno) {this.produtividadeAno = produtividadeAno;}
    public StatusSafra getStatus() {return status;}
    public void setStatus(StatusSafra status) {this.status = status;}
    public Long getTalhaoId() {return talhaoId;}
    public void setTalhaoId(Long talhaoId) {this.talhaoId = talhaoId;}
    public Long getTipoSoloId() {return tipoSoloId;}
    public void setTipoSoloId(Long tipoSoloId) {this.tipoSoloId = tipoSoloId;}

    private Long culturaId;
    private String culturaNome;
    private Long talhaoId;
    private Double area;
    private Map<String, Float> produtividadeAno;
    private Long tipoSoloId;
    private Long areaAgricolaId;
    private String arquivoDaninha;
    private String arquivoFinalDaninha;
}
