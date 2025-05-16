package fatec.porygon.dto;

import java.time.LocalDateTime;

public class SafraGeoJsonDto {
    private String idSafra;
    private String arquivoFazenda;
    private String arquivoDaninha;
    private String arquivoFinalDaninha;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaVersao;

    // Getters e Setters
    public String getIdSafra() {
        return idSafra;
    }

    public void setIdSafra(String idSafra) {
        this.idSafra = idSafra;
    }

    public String getArquivoFazenda() {
        return arquivoFazenda;
    }

    public void setArquivoFazenda(String arquivoFazenda) {
        this.arquivoFazenda = arquivoFazenda;
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

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getDataUltimaVersao() {
        return dataUltimaVersao;
    }

    public void setDataUltimaVersao(LocalDateTime dataUltimaVersao) {
        this.dataUltimaVersao = dataUltimaVersao;
    }
}