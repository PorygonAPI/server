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
    private Usuario usuario_id;

    @ManyToOne
    @JoinColumn(name = "usuario_upgrade_id")
    private Usuario usuario_upgrade_id;

    @ManyToOne
    @JoinColumn(name = "usuario_aprovador_id")
    private Usuario usuario_aprovador_id;

    @Column(name = "nome_fazenda", nullable = false)
    private String nome_fazenda;

    @Column(name = "cultura", nullable = false)
    private String cultura;

    @Column(name = "produtividade_ano", nullable = false)
    private Double produtividade_ano;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "tipo_solo", nullable = false)
    private String tipo_solo;

    @Column(name = "cidade", nullable = false)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @Column(name = "vetor_raiz", nullable = false, columnDefinition = "JSON")
    private String vetor_raiz;

    @Column(name = "vetor_atualizado", columnDefinition = "JSON")
    private String vetor_atualizado;

    @Column(name = "vetor_aprovado", columnDefinition = "JSON")
    private String vetor_aprovado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusArea status = StatusArea.pendente;

    // Getters
    public Long getId() {
        return id;
    }

    public Usuario getusuario_id() {
        return usuario_id;
    }

    public Usuario getUsuarioUpgrade() {
        return usuario_upgrade_id;
    }

    public Usuario getusuario_aprovador_id() {
        return usuario_aprovador_id;
    }

    public String getnome_fazenda() {
        return nome_fazenda;
    }

    public String getCultura() {
        return cultura;
    }

    public Double getprodutividade_ano() {
        return produtividade_ano;
    }

    public Double getArea() {
        return area;
    }

    public String gettipo_solo() {
        return tipo_solo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getvetor_raiz() {
        return vetor_raiz;
    }

    public String getvetor_atualizado() {
        return vetor_atualizado;
    }

    public String getvetor_aprovado() {
        return vetor_aprovado;
    }

    public StatusArea getStatus() {
        return status;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setUsuarioUpgrade(Usuario usuario_upgrade_id) {
        this.usuario_upgrade_id = usuario_upgrade_id;
    }

    public void setUsuarioAprovador(Usuario usuario_aprovador_id) {
        this.usuario_aprovador_id = usuario_aprovador_id;
    }

    public void setnome_fazenda(String nome_fazenda) {
        this.nome_fazenda = nome_fazenda;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

    public void setprodutividade_ano(Double produtividade_ano) {
        this.produtividade_ano = produtividade_ano;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public void settipo_solo(String tipo_solo) {
        this.tipo_solo = tipo_solo;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setvetor_raiz(String vetor_raiz) {
        this.vetor_raiz = vetor_raiz;
    }

    public void setvetor_atualizado(String vetor_atualizado) {
        this.vetor_atualizado = vetor_atualizado;
    }

    public void setvetor_aprovado(String vetor_aprovado) {
        this.vetor_aprovado = vetor_aprovado;
    }

    public void setStatus(StatusArea status) {
        this.status = status;
    }
}