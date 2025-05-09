
public class StatusRelatorioDto {
    private int totalPendentes;
    private int totalAtribuidos;
    private int totalAprovados;

    public int getTotalPendentes() {
        return totalPendentes;
    }
    public void setTotalPendentes(int totalPendentes) {
        this.totalPendentes = totalPendentes;
    }
    public int getTotalAtribuidos() {
        return totalAtribuidos;
    }
    public void setTotalAtribuidos(int totalAtribuidos) {
        this.totalAtribuidos = totalAtribuidos;
    }
    public int getTotalAprovados() {
        return totalAprovados;
    }
    public void setTotalAprovados(int totalAprovados) {
        this.totalAprovados = totalAprovados;
    }
    public StatusRelatorioDto(int totalPendentes, int totalAtribuidos, int totalAprovados) {
        this.totalPendentes = totalPendentes;
        this.totalAtribuidos = totalAtribuidos;
        this.totalAprovados = totalAprovados;
    }
}
