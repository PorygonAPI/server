package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;

public class AreaAgricolaDto {
    private Long id;
    private Long usuarioId;
    private Long usuarioUpgradeId;
    private Long usuarioAprovadorId;
    private String nomeFazenda;
    private String cultura;
    private Double produtividadeAno;
    private Double area;
    private String tipoSolo;
    private String cidade;
    private String estado;
    private String vetorRaiz;
    private String vetorAtualizado;
    private String vetorAprovado;
    private StatusArea status;

    // Getters
    public Long getId() { return id; }
    public Long getUsuarioId() { return usuarioId; }
    public Long getUsuarioUpgradeId() { return usuarioUpgradeId; }
    public Long getUsuarioAprovadorId() { return usuarioAprovadorId; }
    public String getNomeFazenda() { return nomeFazenda; }
    public String getCultura() { return cultura; }
    public Double getProdutividadeAno() { return produtividadeAno; }
    public Double getArea() { return area; }
    public String getTipoSolo() { return tipoSolo; }
    public String getCidade() { return cidade; }
    public String getEstado() { return estado; }
    public String getVetorRaiz() { return vetorRaiz; }
    public String getVetorAtualizado() { return vetorAtualizado; }
    public String getVetorAprovado() { return vetorAprovado; }
    public StatusArea getStatus() { return status; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setUsuarioUpgradeId(Long usuarioUpgradeId) { this.usuarioUpgradeId = usuarioUpgradeId; }
    public void setUsuarioAprovadorId(Long usuarioAprovadorId) { this.usuarioAprovadorId = usuarioAprovadorId; }
    public void setNomeFazenda(String nomeFazenda) { this.nomeFazenda = nomeFazenda; }
    public void setCultura(String cultura) { this.cultura = cultura; }
    public void setProdutividadeAno(Double produtividadeAno) { this.produtividadeAno = produtividadeAno; }
    public void setArea(Double area) { this.area = area; }
    public void setTipoSolo(String tipoSolo) { this.tipoSolo = tipoSolo; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setVetorRaiz(String vetorRaiz) { this.vetorRaiz = vetorRaiz; }
    public void setVetorAtualizado(String vetorAtualizado) { this.vetorAtualizado = vetorAtualizado; }
    public void setVetorAprovado(String vetorAprovado) { this.vetorAprovado = vetorAprovado; }
    public void setStatus(StatusArea status) { this.status = status; }
}