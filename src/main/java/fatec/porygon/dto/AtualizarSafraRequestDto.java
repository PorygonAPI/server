package fatec.porygon.dto;

public class AtualizarSafraRequestDto {
    private Long idTalhao;
    private Integer anoSafra;
    private String culturaNome;
    private Double produtividadeAno;
    private String tipoSoloNome;
    private Double area;

    // Getters and Setters
    public Long getIdTalhao() { return idTalhao; }
    public void setIdTalhao(Long novoIdTalhao) { this.idTalhao = novoIdTalhao; }

    public Integer getAnoSafra() { return anoSafra; }
    public void setAnoSafra(Integer anoSafra) { this.anoSafra = anoSafra; }

    public String getCulturaNome() { return culturaNome; }
    public void setCulturaNome(String culturaNome) { this.culturaNome = culturaNome; }

    public Double getProdutividadeAno() { return produtividadeAno; }
    public void setProdutividadeAno(Double produtividadeAno) { this.produtividadeAno = produtividadeAno; }

    public String getTipoSoloNome() { return tipoSoloNome; }
    public void setTipoSoloNome(String tipoSoloNome) { this.tipoSoloNome = tipoSoloNome; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
}