package fatec.porygon.service;

import fatec.porygon.dto.LogDto;
import fatec.porygon.entity.Log;
import fatec.porygon.repository.LogRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<LogDto> buscarTodos() {
        return logRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    private LogDto converterParaDto(Log log) {
        LogDto dto = new LogDto();
        dto.setId(log.getId());
        dto.setUsuarioId(log.getUsuario().getId());  
        dto.setDataHora(log.getDataHora());         
        dto.setAcao(log.getAcao());
        return dto;
    }
}