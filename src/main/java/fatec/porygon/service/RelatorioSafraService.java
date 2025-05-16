package fatec.porygon.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RelatorioSafraService {
    @Autowired
    private SafraRepository safraRepository;

    public String calcularDuracao(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) return "Desconhecido";
        Duration duracao = Duration.between(inicio, fim);
        long dias = duracao.toDays();
        long horas = duracao.toHours() % 24;
        long minutos = duracao.toMinutes() % 60;

        List<Safra> safrasAprovadas = safraRepository.findByStatus(StatusSafra.Aprovado);
        return String.format("%d dias, %d horas, %d minutos", dias, horas, minutos);
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
                    safra.getDataAtribuicao(),
                    safra.getDataAprovacao(),
                    tempo
            );
        }).toList();
    }
}

