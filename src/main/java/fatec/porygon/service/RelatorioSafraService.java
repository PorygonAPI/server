package fatec.porygon.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

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
}

