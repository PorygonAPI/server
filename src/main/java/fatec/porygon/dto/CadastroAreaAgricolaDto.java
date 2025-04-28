package fatec.porygon.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CadastroAreaAgricolaDto {
    private String nomeFazenda;
    private String estado;
    private String cidadeNome;
    private String arquivoFazenda;
    private String arquivoErvaDaninha;

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidadeNome() {
        return cidadeNome;
    }

    public void setCidadeNome(String cidadeNome) {
        this.cidadeNome = cidadeNome;
    }

    public String getArquivoFazenda() {
        return arquivoFazenda;
    }

    public void setArquivoFazenda(String arquivoFazenda) {
        this.arquivoFazenda = arquivoFazenda;
    }

    public String getArquivoErvaDaninha() {
        return arquivoErvaDaninha;
    }

    public void setArquivoErvaDaninha(String arquivoErvaDaninha) {
        this.arquivoErvaDaninha = arquivoErvaDaninha;
    }
}

