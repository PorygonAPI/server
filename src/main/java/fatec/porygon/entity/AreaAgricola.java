package fatec.porygon.entity;

import fatec.porygon.enums.StatusArea;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Geometry;

@Entity
@Table(name = "area_agricola")
public class AreaAgricola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_fazenda", nullable = false)
    private String nomeFazenda;

    @Column(name = "estado", nullable = false, length = 2)
    private String estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusArea status = StatusArea.Pendente;

    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidadeId;

    @Column(name = "arquivo_fazenda", nullable = false, columnDefinition = "GEOMETRY")
    private Geometry arquivoFazenda;

    // Getters
    public Long getId() {return id;}

    public String getNomeFazenda() {return nomeFazenda;}

    public String getEstado() {return estado;}

    public StatusArea getStatus() {return status;}

    public Cidade getCidadeId() {return cidadeId;}

    public Geometry getArquivoFazenda() {return arquivoFazenda;}

    // Setters
    public void setId(Long id) {this.id = id;}

    public void setNomeFazenda(String nomeFazenda) {this.nomeFazenda = nomeFazenda;}

    public void setEstado(String estado) {this.estado = estado;}

    public void setStatus(StatusArea status) {this.status = status;}

    public void setCidadeId(Cidade cidadeId) {this.cidadeId = cidadeId;}

    public void setArquivoFazenda(Geometry arquivoFazenda) {this.arquivoFazenda = arquivoFazenda;}
}