package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;

public class AreaAgricolaDto {
    private Long id;
    private Long usuario_id;
    private Long usuario_upgrade_id;
    private Long usuario_aprovador_id;
    private String nome_fazenda;
    private String cultura;
    private Double produtividade_ano;
    private Double area;
    private String tipo_solo;
    private String cidade;
    private String estado;
    private String vetor_raiz;
    private String vetor_atualizado;
    private String vetor_aprovado;
    private StatusArea status;

    // Getters
    public Long getId() { return id; }
    public Long getusuario_id() { return usuario_id; }
    public Long getusuario_upgrade_id() { return usuario_upgrade_id; }
    public Long getusuario_aprovador_id() { return usuario_aprovador_id; }
    public String getnome_fazenda() { return nome_fazenda; }
    public String getCultura() { return cultura; }
    public Double getprodutividade_ano() { return produtividade_ano; }
    public Double getArea() { return area; }
    public String gettipo_solo() { return tipo_solo; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getvetor_raiz() { return vetor_raiz; }
    public String getvetor_atualizado() { return vetor_atualizado; }
    public String getvetor_aprovado() { return vetor_aprovado; }
    public StatusArea getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setusuario_id(Long usuario_id) { this.usuario_id = usuario_id; }
    public void setusuario_upgrade_id(Long usuario_upgrade_id) { this.usuario_upgrade_id = usuario_upgrade_id; }
    public void setusuario_aprovador_id(Long usuario_aprovador_id) { this.usuario_aprovador_id = usuario_aprovador_id; }
    public void setnome_fazenda(String nome_fazenda) { this.nome_fazenda = nome_fazenda; }
    public void setCultura(String cultura) { this.cultura = cultura; }
    public void setprodutividade_ano(Double produtividade_ano) { this.produtividade_ano = produtividade_ano; }
    public void setArea(Double area) { this.area = area; }
    public void settipo_solo(String tipo_solo) { this.tipo_solo = tipo_solo; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setvetor_raiz(String vetor_raiz) { this.vetor_raiz = vetor_raiz; }
    public void setvetor_atualizado(String vetor_atualizado) { this.vetor_atualizado = vetor_atualizado; }
    public void setvetor_aprovado(String vetor_aprovado) { this.vetor_aprovado = vetor_aprovado; }
    public void setStatus(StatusArea status) { this.status = status; }
}