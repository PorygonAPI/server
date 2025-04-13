package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;

public class AreaAgricolaDto {
    private Long id;
    private String nome_fazenda;
    private String estado;
    private StatusArea status;
    private String cidade_nome;
    private String arquivo_fazenda;
    private String cidade;
    private Long usuario_id;

    // Getters
    public Long getId() { return id; }
    public String getNomeFazenda() { return nome_fazenda; }
    public String getEstado() { return estado; }
    public StatusArea getStatus() { return status; }
    public String getCidadeNome() { return cidade_nome; }
    public String getArquivoFazenda() { return arquivo_fazenda; }
    public String getCidade() { return cidade != null ? cidade : cidade_nome; }
    public Long getusuario_id() { return usuario_id; }
    public String getnome_fazenda() { return nome_fazenda; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNomeFazenda(String nome_fazenda) { this.nome_fazenda = nome_fazenda; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setStatus(StatusArea status) { this.status = status; }
    public void setCidadeNome(String cidade_nome) { this.cidade_nome = cidade_nome; }
    public void setArquivoFazenda(String arquivo_fazenda) { this.arquivo_fazenda = arquivo_fazenda; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setusuario_id(Long usuario_id) { this.usuario_id = usuario_id; }
    public void setnome_fazenda(String nome_fazenda) { this.nome_fazenda = nome_fazenda; }
}