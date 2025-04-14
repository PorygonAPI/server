package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;

public class AreaAgricolaDto {
    private Long id;
    private String nomeFazenda;
    private String estado;
    private StatusArea status;
    private String cidadeNome;
    private String arquivo_fazenda;

    // Getters
    public Long getId() { return id; }
    public String getNomeFazenda() { return nomeFazenda; }
    public String getEstado() { return estado; }
    public StatusArea getStatus() { return status; }
    public String getCidadeNome() { return cidadeNome; }
    public String getArquivoFazenda() { return arquivo_fazenda; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNomeFazenda(String nomeFazenda) { this.nomeFazenda = nomeFazenda; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setStatus(StatusArea status) { this.status = status; }
    public void setCidadeNome(String cidadeNome) { this.cidadeNome = cidadeNome; }
    public void setArquivoFazenda(String arquivo_fazenda) { this.arquivo_fazenda = arquivo_fazenda; }
}
