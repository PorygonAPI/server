package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Column(unique = true, nullable = false)
    private String email;

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    // Getter e Setter para id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter e Setter para nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para senha
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Getter e Setter para cargo
    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}



