package co.ufps.pdau.service;

import co.ufps.pdau.model.Facultad;
import co.ufps.pdau.repository.FacultadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacultadService {
    @Autowired
    private final FacultadRepository facultadRepository;

    public List<Facultad> getAllFacultades() {
        return facultadRepository.findAll();
    }

    public Optional<Facultad> getFacultadById(Long id) {
        return facultadRepository.findById(id);
    }

    public Facultad createFacultad(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    public Facultad updateFacultad(Long id, Facultad facultadDetails) {
        return facultadRepository.findById(id).map(facultad -> {
            facultad.setNombre(facultadDetails.getNombre());
            facultad.setCarreras(facultadDetails.getCarreras());
            return facultadRepository.save(facultad);
        }).orElseThrow(() -> new RuntimeException("Facultad not found"));
    }

    public void deleteFacultad(Long id) {
        facultadRepository.deleteById(id);
    }
}
