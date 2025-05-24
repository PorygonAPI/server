package fatec.porygon.service;

import fatec.porygon.dto.PermissaoDto;
import fatec.porygon.entity.Permissao;
import fatec.porygon.repository.PermissaoRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissaoService {
    
    private final PermissaoRepository permissaoRepository;
    
    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }
    
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<PermissaoDto> buscarTodos() {
        return permissaoRepository.findAll().stream()
            .map(this::converterParaDto)
            .collect(Collectors.toList());
    }

    private PermissaoDto converterParaDto(Permissao permissao) {
        PermissaoDto dto = new PermissaoDto();
        dto.setId(permissao.getId());
        dto.setTipo(permissao.getTipo());
        return dto;
    }
}