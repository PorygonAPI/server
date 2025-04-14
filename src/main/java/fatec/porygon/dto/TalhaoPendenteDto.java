package fatec.porygon.dto;

public class TalhaoPendenteDto {
    private Long id;
    private String nomeFazenda;
    private String cultura;
    private Integer ano;
    private Double produtividade;
    private Double area;
    private String tipoSolo;
    private String cidade;
    private String estado;
    private String status;

    public TalhaoPendenteDto(Long id, String nomeFazenda, String cultura, Integer ano, Double produtividade, Double area, String tipoSolo, String cidade, String estado, String status) {
        this.id = id;
        this.nomeFazenda = nomeFazenda;
        this.cultura = cultura;
        this.ano = ano;
        this.produtividade = produtividade;
        this.area = area;
        this.tipoSolo = tipoSolo;
        this.cidade = cidade;
        this.estado = estado;
        this.status = status;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNome_fazenda() { return nomeFazenda;}

    public void setNome_fazenda(String nomeFazenda) {this.nomeFazenda = nomeFazenda;}

    public String getCultura() {return cultura;}
    
    public void setCultura(String cultura) {this.cultura = cultura;}

    public Double getProdutividade() {return produtividade;}

    public void setProdutividade(Double produtividade) {this.produtividade = produtividade;}

    public Double getArea() {return area;}

    public void setArea(Double area) {this.area = area;}

    public String getTipo_solo() {return tipoSolo;}

    public void setTipo_solo(String tipoSolo) {this.tipoSolo = tipoSolo;}

    public String getCidade() {return cidade;}

    public void setCidade(String cidade) {this.cidade = cidade;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public Integer getAno() {return ano;}

    public void setAno(Integer ano) {this.ano = ano;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}



