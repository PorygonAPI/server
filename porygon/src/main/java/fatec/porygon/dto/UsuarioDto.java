package fatec.porygon.dto;

public class UsuarioDto {
    private Long id;
    private String nome;
    private String senha;
    private String cargoNome; // Exibe apenas o nome do cargo, não a entidade Cargo
    private Long cargoId;
    private String email;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para cargoNome
    public String getCargoNome() {
        return cargoNome;
    }

    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }

    public Long getCargoId() {return cargoId;}
    public void setCargoId(Long cargoId) {this.cargoId = cargoId;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}