package fatec.porygon.controller;

import fatec.porygon.dto.LogDto;
import fatec.porygon.service.LogService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<LogDto>> buscarTodos() {
        return ResponseEntity.ok(logService.buscarTodos());
    }

}