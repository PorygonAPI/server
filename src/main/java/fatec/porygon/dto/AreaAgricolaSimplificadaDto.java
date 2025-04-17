package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;
import org.locationtech.jts.geom.Geometry;

public class AreaAgricolaSimplificadaDto {
    private String nome;
    private String cidade;
    private String estado;
    private StatusArea status;
    private String arquivoFazenda;

    public AreaAgricolaSimplificadaDto() {}

    public AreaAgricolaSimplificadaDto(String nome, String cidade, String estado, StatusArea status, String arquivoFazenda) {
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.status = status;
        this.arquivoFazenda = arquivoFazenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public StatusArea getStatus() {
        return status;
    }

    public void setStatus(StatusArea status) {
        this.status = status;
    }

    public String getArquivoFazenda() {
        return arquivoFazenda;
    }
    public void setArquivoFazenda(String arquivoFazenda) {
        this.arquivoFazenda = arquivoFazenda;
    }
}
