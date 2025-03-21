package fatec.porygon.service;

import fatec.porygon.entity.Log;
import fatec.porygon.repository.LogRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LogService {
    
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    public List<Log> buscarTodos(){
        return logRepository.findAll();
    }
}
