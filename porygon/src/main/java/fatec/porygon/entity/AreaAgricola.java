package fatec.porygon.entity;

import fatec.porygon.enums.StatusArea;
import jakarta.persistence.*;

@Entity
@Table(name = "area_agricola")
public class AreaAgricola {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_upgrade_id")
    private Usuario usuarioUpgradeId;

    @ManyToOne
    @JoinColumn(name = "usuario_aprovador_id")
    private Usuario usuarioAprovadorId;

    @Column(name = "nome_fazenda", nullable = false)
    private String nomeFazenda;

    @Column(name = "cultura", nullable = false)
    private String cultura;

    @Column(name = "produtividade_ano", nullable = false)
    private Double produtividadeAno;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "tipo_solo", nullable = false)
    private String tipoSolo;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @Column(name = "vetor_raiz", nullable = false, columnDefinition = "JSON")
    private String vetorRaiz;

    @Column(name = "vetor_atualizado", columnDefinition = "JSON")
    private String vetorAtualizado;

    @Column(name = "vetor_aprovado", columnDefinition = "JSON")
    private String vetorAprovado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusArea status = StatusArea.PENDENTE;

    // Getters
    public Long getId() {
        return id;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public Usuario getUsuarioUpgrade() {
        return usuarioUpgradeId;
    }

    public Usuario getUsuarioAprovadorId() {
        return usuarioAprovadorId;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public String getCultura() {
        return cultura;
    }

    public Double getProdutividadeAno() {
        return produtividadeAno;
    }

    public Double getArea() {
        return area;
    }

    public String getTipoSolo() {
        return tipoSolo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getVetorRaiz() {
        return vetorRaiz;
    }

    public String getVetorAtualizado() {
        return vetorAtualizado;
    }

    public String getVetorAprovado() {
        return vetorAprovado;
    }

    public StatusArea getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setUsuarioUpgrade(Usuario usuarioUpgradeId) {
        this.usuarioUpgradeId = usuarioUpgradeId;
    }

    public void setUsuarioAprovador(Usuario usuarioAprovadorId) {
        this.usuarioAprovadorId = usuarioAprovadorId;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public void setProdutividadeAno(Double produtividadeAno) {
        this.produtividadeAno = produtividadeAno;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void setTipoSolo(String tipoSolo) {
        this.tipoSolo = tipoSolo;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setVetorRaiz(String vetorRaiz) {
        this.vetorRaiz = vetorRaiz;
    }

    public void setVetorAtualizado(String vetorAtualizado) {
        this.vetorAtualizado = vetorAtualizado;
    }

    public void setVetorAprovado(String vetorAprovado) {
        this.vetorAprovado = vetorAprovado;
    }

    public void setStatus(StatusArea status) {
        this.status = status;
    }
}