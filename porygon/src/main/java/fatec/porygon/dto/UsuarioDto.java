package fatec.porygon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    private Long id;
    private String nome;
    private String senha;
    private String cargoNome; // Em vez de expor a entidade Cargo, apenas mostra o nome do cargo
}

