package fatec.porygon.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioID;

    @Column(nullable = false)
    private String acao;

    @Column(nullable = false)
    private LocalDateTime dataHora;
    
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Usuario getUsuarioID() {return usuarioID;}
    public void setUsuarioID(Usuario usuario) {this.usuario = usuarioID;}
    public String getAcao() {return acao;}
    public void setAcao(String acao) {this.acao = acao;}
    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}
}
