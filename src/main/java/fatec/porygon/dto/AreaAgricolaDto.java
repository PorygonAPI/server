package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;
import org.locationtech.jts.geom.Geometry;

public class AreaAgricolaDto {
    private Long id;
    private String nomeFazenda;
    private String estado;
    private StatusArea status;
    private String cidadeNome;
    private Geometry arquivoFazenda;

    // Getters
    public Long getId() { return id; }
    public String getNomeFazenda() { return nomeFazenda; }
    public String getEstado() { return estado; }
    public StatusArea getStatus() { return status; }
    public String getCidadeNome() { return cidadeNome; }
    public Geometry getArquivoFazenda() { return arquivoFazenda; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNomeFazenda(String nomeFazenda) { this.nomeFazenda = nomeFazenda; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setStatus(StatusArea status) { this.status = status; }
    public void setCidadeNome(String cidadeNome) { this.cidadeNome = cidadeNome; }
    public void setArquivoFazenda(Geometry arquivoFazenda) { this.arquivoFazenda = arquivoFazenda; }
}