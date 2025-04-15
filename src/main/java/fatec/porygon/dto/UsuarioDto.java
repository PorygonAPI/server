package fatec.porygon.dto;

public class UsuarioDto {
    private Long id;
    private String nome;
    private String senha;
    private String email;
    private Long cargo;
    private String cargoNome;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Long getCargo() {return cargo;}
    public void setCargo(Long cargo) {this.cargo = cargo;}
    
    public String getCargoNome() {return cargoNome;}
    public void setCargoNome(String cargoNome) {this.cargoNome = cargoNome;}
}