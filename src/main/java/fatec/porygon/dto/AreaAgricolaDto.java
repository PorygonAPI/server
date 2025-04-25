package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;

import java.time.LocalDateTime;

public class AreaAgricolaDto {
    private Long id;
    private String nomeFazenda;
    private String estado;
    private StatusArea status;
    private String arquivoFazenda;
    private String cidadeNome;
    private LocalDateTime dataCadastro;

    // Getters
    public Long getId() {return id;}
    public String getNomeFazenda() {return nomeFazenda;}
    public String getEstado() {return estado;}
    public StatusArea getStatus() {return status;}
    public String getArquivoFazenda() {return arquivoFazenda;}
    public String getCidadeNome() {return cidadeNome;}
    public LocalDateTime getDataCadastro() {return dataCadastro;}

    // Setters
    public void setId(Long id) {this.id = id;}
    public void setNomeFazenda(String nomeFazenda) {this.nomeFazenda = nomeFazenda;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setStatus(StatusArea status) {this.status = status;}
    public void setArquivoFazenda(String arquivoFazenda) {this.arquivoFazenda = arquivoFazenda;}
    public void setCidadeNome(String cidadeNome) {this.cidadeNome = cidadeNome;}
    public void setDataCadastro(LocalDateTime dataCadastro) {this.dataCadastro = dataCadastro;}
}
