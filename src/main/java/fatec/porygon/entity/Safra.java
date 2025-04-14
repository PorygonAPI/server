package fatec.porygon.entity;

import fatec.porygon.enums.StatusSafra;
import jakarta.persistence.*;

@Entity
public class Safra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "cultura_id")
    private Cultura cultura;

    @Column(columnDefinition = "geometry")
    private String arquivo_daninha;

    @Column(columnDefinition = "geometry")
    private String arquivo_final_daninha;

    @ManyToOne
    @JoinColumn(name = "talhao_id")
    private Talhao talhao;

    @Enumerated(EnumType.STRING)
    private StatusSafra status;

    public Integer getAno() {return ano;}
    public void setAno(Integer ano) {this.ano = ano;}

    public Cultura getCultura() {return cultura;}
    public void setCultura(Cultura cultura) {this.cultura = cultura;}

    public String getArquivo_Daninha() {return arquivo_daninha;}
    public void setArquivo_Daninha(String arquivo_daninha) {this.arquivo_daninha = arquivo_daninha;}

    public String getArquivo_Final_Daninha() {return arquivo_final_daninha;}
    public void setArquivo_Final_Daninha(String arquivo_final_daninha) {this.arquivo_final_daninha = arquivo_final_daninha;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public StatusSafra getStatus() {return status;}
    public void setStatus(StatusSafra status) {this.status = status;}

    public Talhao getTalhao() {return talhao;}
    public void setTalhao(Talhao talhao) {this.talhao = talhao;}
}
