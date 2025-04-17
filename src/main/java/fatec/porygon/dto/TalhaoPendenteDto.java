package fatec.porygon.dto;

public class TalhaoPendenteDto {
    private Long id;
    private String nomeFazenda;
    private String cultura;
    private Double produtividadeAno;
    private Double area;
    private String tipoSolo;
    private String cidade;
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public Double getProdutividadeAno() {
        return produtividadeAno;
    }

    public void setProdutividadeAno(Double produtividadeAno) {
        this.produtividadeAno = produtividadeAno;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getTipoSolo() {
        return tipoSolo;
    }

    public void setTipoSolo(String tipoSolo) {
        this.tipoSolo = tipoSolo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TalhaoPendenteDto(Long id, String nomeFazenda, String cultura, Double produtividadeAno,
                             Double area, String tipoSolo, String cidade, String estado) {
        this.id = id;
        this.nomeFazenda = nomeFazenda;
        this.cultura = cultura;
        this.produtividadeAno = produtividadeAno;
        this.area = area;
        this.tipoSolo = tipoSolo;
        this.cidade = cidade;
        this.estado = estado;
    }
}
