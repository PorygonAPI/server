package fatec.porygon.dto;

import java.time.LocalDateTime;

public class SafraGeoJsonDto {
    private String idSafra;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaVersao;

    public String getIdSafra() {
        return idSafra;
    }

    public void setIdSafra(String idSafra) {
        this.idSafra = idSafra;
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