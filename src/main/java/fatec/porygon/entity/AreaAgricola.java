package fatec.porygon.entity;

import fatec.porygon.enums.StatusArea;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "area_agricola")
public class AreaAgricola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_fazenda", nullable = false)
    private String nome_fazenda;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusArea status = StatusArea.Pendente;

    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade_id;

    @Column(name = "arquivo_fazenda", nullable = false, columnDefinition = "GEOMETRY")
    private Geometry arquivo_fazenda;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario_id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_upgrade_id")
    private Usuario usuarioUpgrade;
    
    @ManyToOne
    @JoinColumn(name = "usuario_aprovador_id")
    private Usuario usuario_aprovador_id;
    
    @Column(name = "cultura")
    private String cultura;
    
    @Column(name = "produtividade_ano")
    private Double produtividade_ano;
    
    @Column(name = "area")
    private Double area;
    
    @Column(name = "tipo_solo")
    private String tipo_solo;
    
    @Column(name = "vetor_raiz", columnDefinition = "TEXT")
    private String vetor_raiz;
    
    @Column(name = "vetor_atualizado", columnDefinition = "TEXT")
    private String vetor_atualizado;
    
    @Column(name = "vetor_aprovado", columnDefinition = "TEXT")
    private String vetor_aprovado;

    // Getters
    public Long getId() {return id;}
    public String getNomeFazenda() {return nome_fazenda;}
    public String getEstado() {return estado;}
    public StatusArea getStatus() {return status;}
    public Cidade getCidadeId() {return cidade_id;}
    public Geometry getArquivoFazenda() {return arquivo_fazenda;}
    public Usuario getusuario_id() {return usuario_id;}
    public Usuario getUsuarioUpgrade() {return usuarioUpgrade;}
    public Usuario getusuario_aprovador_id() {return usuario_aprovador_id;}
    public String getCultura() {return cultura;}
    public Double getprodutividade_ano() {return produtividade_ano;}
    public Double getArea() {return area;}
    public String gettipo_solo() {return tipo_solo;}
    public String getvetor_raiz() {return vetor_raiz;}
    public String getvetor_atualizado() {return vetor_atualizado;}
    public String getvetor_aprovado() {return vetor_aprovado;}

    // Setters
    public void setId(Long id) {this.id = id;}
    public void setNomeFazenda(String nome_fazenda) {this.nome_fazenda = nome_fazenda;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setStatus(StatusArea status) {this.status = status;}
    public void setCidadeId(Cidade cidade_id) {this.cidade_id = cidade_id;}
    public void setArquivoFazenda(Geometry arquivo_fazenda) {this.arquivo_fazenda = arquivo_fazenda;}
    public void setUsuario(Usuario usuario_id) {this.usuario_id = usuario_id;}
    public void setUsuarioUpgrade(Usuario usuarioUpgrade) {this.usuarioUpgrade = usuarioUpgrade;}
    public void setUsuarioAprovador(Usuario usuario_aprovador_id) {this.usuario_aprovador_id = usuario_aprovador_id;}
    public void setCultura(String cultura) {this.cultura = cultura;}
    public void setprodutividade_ano(Double produtividade_ano) {this.produtividade_ano = produtividade_ano;}
    public void setArea(Double area) {this.area = area;}
    public void settipo_solo(String tipo_solo) {this.tipo_solo = tipo_solo;}
    public void setvetor_raiz(String vetor_raiz) {this.vetor_raiz = vetor_raiz;}
    public void setvetor_atualizado(String vetor_atualizado) {this.vetor_atualizado = vetor_atualizado;}
    public void setvetor_aprovado(String vetor_aprovado) {this.vetor_aprovado = vetor_aprovado;}
}