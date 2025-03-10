package fatec.porygon.dto;

public class CargoDto {
    private Long id; // A variável id é declarada uma vez, não precisa ser repetida.
    private String nome; // A variável nome também é declarada uma vez.

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
}
