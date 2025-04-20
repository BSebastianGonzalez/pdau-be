package co.ufps.pdau.service;

import co.ufps.pdau.DTO.CarreraRequestDTO;
import co.ufps.pdau.model.Carrera;
import co.ufps.pdau.model.Facultad;
import co.ufps.pdau.repository.CarreraRepository;
import co.ufps.pdau.repository.FacultadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarreraService {
    @Autowired
    private final CarreraRepository carreraRepository;
    @Autowired
    private FacultadRepository facultadRepository;

    public List<Carrera> getAllCarreras() {
        return carreraRepository.findAll();
    }

    public Optional<Carrera> getCarreraById(Long id) {
        return carreraRepository.findById(id);
    }

    public Carrera createCarrera(CarreraRequestDTO dto) {
        Facultad facultad = facultadRepository.findById(dto.getFacultadId())
                .orElseThrow(() -> new RuntimeException("Facultad no encontrada"));

        Carrera carrera = new Carrera();
        carrera.setId(dto.getId());
        carrera.setNombre(dto.getNombre());
        carrera.setFacultad(facultad);

        return carreraRepository.save(carrera);
    }

    public Carrera updateCarrera(Long id, Carrera carreraDetails) {
        return carreraRepository.findById(id).map(carrera -> {
            carrera.setNombre(carreraDetails.getNombre());
            carrera.setFacultad(carreraDetails.getFacultad());
            return carreraRepository.save(carrera);
        }).orElseThrow(() -> new RuntimeException("Carrera not found"));
    }

    public void deleteCarrera(Long id) {
        carreraRepository.deleteById(id);
    }
}

