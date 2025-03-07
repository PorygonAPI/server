package fatec.porygon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String nome;
}

