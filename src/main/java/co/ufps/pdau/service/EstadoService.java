package co.ufps.pdau.service;

import co.ufps.pdau.model.Estado;
import co.ufps.pdau.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoService {
    @Autowired
    private final EstadoRepository estadoRepository;

    public List<Estado> getAllEstados() {
        return estadoRepository.findAll();
    }

    public Optional<Estado> getEstadoById(Long id) {
        return estadoRepository.findById(id);
    }

    public Estado createEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado updateEstado(Long id, Estado estadoDetails) {
        return estadoRepository.findById(id).map(estado -> {
            estado.setNombre(estadoDetails.getNombre());
            estado.setDescripcion(estadoDetails.getDescripcion());
            return estadoRepository.save(estado);
        }).orElseThrow(() -> new RuntimeException("Estado not found"));
    }

    public void deleteEstado(Long id) {
        estadoRepository.deleteById(id);
    }
}