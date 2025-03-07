package fatec.porygon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargoNome() {
        return cargoNome;
    }

    public void setCargoNome(String cargoNome) {
        this.cargoNome = cargoNome;
    }

    private Long id;
    private String nome;
    private String senha;
    private String cargoNome; // Em vez de expor a entidade Cargo, apenas mostra o nome do cargo

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}
}

