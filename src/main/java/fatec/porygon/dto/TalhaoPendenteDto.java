package fatec.porygon.dto;

public class TalhaoPendenteDto {
    private Long id;
    private String nome_fazenda;
    private String cultura;
    private Double produtividade;
    private Double area;
    private String tipo_solo;
    private String cidade;
    private String estado;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNome_fazenda() { return nome_fazenda;}

    public void setNome_fazenda(String nome_fazenda) {this.nome_fazenda = nome_fazenda;}

    public String getCultura() {return cultura;}
    
    public void setCultura(String cultura) {this.cultura = cultura;}

    public Double getProdutividade() {return produtividade;}

    public void setProdutividade(Double produtividade) {this.produtividade = produtividade;}

    public Double getArea() {return area;}

    public void setArea(Double area) {this.area = area;}

    public String getTipo_solo() {return tipo_solo;}

    public void setTipo_solo(String tipo_solo) {this.tipo_solo = tipo_solo;}

    public String getCidade() {return cidade;}

    public void setCidade(String cidade) {this.cidade = cidade;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public TalhaoPendenteDto(Long id, String Nome_fazenda, String cultura, Double produtividade, Double area,
                             String Tipo_solo, String cidade, String estado) {
        this.id = id;
        this.nomeFazenda = Nome_fazenda;
        this.cultura = cultura;
        this.produtividade = produtividade;
        this.area = area;
        this.tipoSolo = Tipo_solo;
        this.cidade = cidade;
        this.estado = estado;
    }
}


