package fatec.porygon.entity;

import fatec.porygon.enums.StatusSafra;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private String arquivoDaninha;

    @Column(name = "arquivo_final_daninha", columnDefinition = "geometry")
    private String arquivoFinalDaninha;

    @Enumerated(EnumType.STRING)
    private StatusSafra status;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_ultima_versao")
    private LocalDateTime dataUltimaVersao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public Double getProdutividadeAno() { return produtividadeAno; }
    public void setProdutividadeAno(Double produtividadeAno) { this.produtividadeAno = produtividadeAno; }

    public String getArquivoDaninha() { return arquivoDaninha; }
    public void setArquivoDaninha(String arquivoDaninha) { this.arquivoDaninha = arquivoDaninha; }

    public String getArquivoFinalDaninha() { return arquivoFinalDaninha; }
    public void setArquivoFinalDaninha(String arquivoFinalDaninha) { this.arquivoFinalDaninha = arquivoFinalDaninha; }

    public StatusSafra getStatus() { return status; }
    public void setStatus(StatusSafra status) { this.status = status; }

    public Talhao getTalhao() { return talhao; }
    public void setTalhao(Talhao talhao) { this.talhao = talhao; }

    public Cultura getCultura() { return cultura; }
    public void setCultura(Cultura cultura) { this.cultura = cultura; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public LocalDateTime getDataUltimaVersao() { return dataUltimaVersao; }
    public void setDataUltimaVersao(LocalDateTime dataUltimaVersao) { this.dataUltimaVersao = dataUltimaVersao; }
}
