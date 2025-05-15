package fatec.porygon.service;

import org.springframework.stereotype.Service;

import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatorioSafraService {

    public String calcularDuracao(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) return "Desconhecido";
        Duration duracao = Duration.between(inicio, fim);
        long dias = duracao.toDays();
        long horas = duracao.toHours() % 24;
        long minutos = duracao.toMinutes() % 60;

        return String.format("%d dias, %d horas e %d minutos", dias, horas, minutos);
    }

    public List<SafraRelatorioDto> gerarRelatorioSafrasAprovadas() {
        List<Safra> safrasAprovadas = safraRepository.findByStatus(StatusSafra.Aprovado);

        return safrasAprovadas.stream().map(safra -> {
            String tempo = calcularDuracao(
                    safra.getDataCadastro(), safra.getDataAprovacao()
            );

            return new SafraRelatorioDto(
                    safra.getId(),
                    safra.getUsuarioAnalista().getNome(),
                    safra.getDataCadastro(),
                    safra.getDataAprovacao(),
                    tempo
            );
        }).toList();
    }
}

