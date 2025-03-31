package fatec.porygon.dto;

public class UsuarioDto {
    private Long id;
    private String nome;
    private String senha;
    private String cargoNome;
    private Long cargo_Id;
    private String email;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getCargoNome() {
        return cargoNome;
    }
    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }

    public Long getCargoId() {
        return cargo_Id;
    }

    public void setCargoId(Long cargoId) {
        this.cargo_Id = cargoId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}