package fatec.porygon.dto;

import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;
import jakarta.persistence.*;

import java.util.List;

public class SafraFazendaDetalhadaDTO {
    private Long id;
    private Integer ano;
    private Double produtividadeAno;
    private String arquivoDaninha;
    private String arquivoFinalDaninha;
    private StatusSafra status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getProdutividadeAno() {
        return produtividadeAno;
    }

    public void setProdutividadeAno(Double produtividadeAno) {
        this.produtividadeAno = produtividadeAno;
    }

    public String getArquivoDaninha() {
        return arquivoDaninha;
    }

    public void setArquivoDaninha(String arquivoDaninha) {
        this.arquivoDaninha = arquivoDaninha;
    }

    public String getArquivoFinalDaninha() {
        return arquivoFinalDaninha;
    }

    public void setArquivoFinalDaninha(String arquivoFinalDaninha) {
        this.arquivoFinalDaninha = arquivoFinalDaninha;
    }

    public StatusSafra getStatus() {
        return status;
    }

    public void setStatus(StatusSafra status) {
        this.status = status;
    }
}
