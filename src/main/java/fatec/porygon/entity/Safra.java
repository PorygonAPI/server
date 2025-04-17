package fatec.porygon.entity;

import fatec.porygon.enums.StatusSafra;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Safra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "produtividade_ano", columnDefinition = "json")
    private Double produtividadeAno;

    @Column(name = "arquivo_daninha", columnDefinition = "geometry")
    private Geometry arquivoDaninha;

    @Column(name = "arquivo_final_daninha", columnDefinition = "geometry")
    private Geometry arquivoFinalDaninha;

    @Enumerated(EnumType.STRING)
    private StatusSafra status;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    @JsonBackReference
    private Talhao talhao;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @ManyToOne
    @JoinColumn(name = "usuario_analista_id")
    private Usuario usuarioAnalista;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Double getProdutividadeAno() { return produtividadeAno; }
    public void setProdutividadeAno(Double produtividadeAno) { this.produtividadeAno = produtividadeAno; }

    public Geometry getArquivoDaninha() { return arquivoDaninha; }
    public void setArquivoDaninha(Geometry arquivoDaninha) { this.arquivoDaninha = arquivoDaninha; }

    public Geometry getArquivoFinalDaninha() { return arquivoFinalDaninha; }
    public void setArquivoFinalDaninha(Geometry arquivoFinalDaninha) { this.arquivoFinalDaninha = arquivoFinalDaninha; }

    public StatusSafra getStatus() { return status; }
    public void setStatus(StatusSafra status) { this.status = status; }

    public Talhao getTalhao() { return talhao; }
    public void setTalhao(Talhao talhao) { this.talhao = talhao; }

    public Cultura getCultura() { return cultura; }
    public void setCultura(Cultura cultura) { this.cultura = cultura; }

    public Usuario getUsuarioAnalista() { return usuarioAnalista; }
    public void setUsuarioAnalista(Usuario usuarioAnalista) { this.usuarioAnalista = usuarioAnalista; }
}