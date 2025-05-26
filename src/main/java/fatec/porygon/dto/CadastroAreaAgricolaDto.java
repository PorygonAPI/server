package fatec.porygon.dto;

import org.springframework.web.multipart.MultipartFile;


public class CadastroAreaAgricolaDto {
    private String nomeFazenda;
    private String estado;
    private String cidadeNome;
    private MultipartFile arquivoFazenda;
    private MultipartFile arquivoErvaDaninha;

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

    public MultipartFile getArquivoFazenda() {
        return arquivoFazenda;
    }

    public void setArquivoFazenda(MultipartFile arquivoFazenda) {
        this.arquivoFazenda = arquivoFazenda;
    }

    public MultipartFile getArquivoErvaDaninha() {
        return arquivoErvaDaninha;
    }

    public void setArquivoErvaDaninha(MultipartFile arquivoErvaDaninha) {
        this.arquivoErvaDaninha = arquivoErvaDaninha;
    }
}

