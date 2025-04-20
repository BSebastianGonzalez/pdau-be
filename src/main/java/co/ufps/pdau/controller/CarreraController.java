package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.CarreraRequestDTO;
import co.ufps.pdau.model.Carrera;
import co.ufps.pdau.service.CarreraService;
import co.ufps.pdau.service.FacultadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carreras")
@RequiredArgsConstructor
public class CarreraController {
    private final CarreraService carreraService;
    private final FacultadService facultadService;

    @GetMapping("/list")
    public List<Carrera> getAllCarreras() {
        return carreraService.getAllCarreras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> getCarreraById(@PathVariable Long id) {
        return carreraService.getCarreraById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Carrera createCarrera(@RequestBody CarreraRequestDTO dto) {
        return carreraService.createCarrera(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrera> updateCarrera(@PathVariable Long id, @RequestBody Carrera carreraDetails) {
        try {
            Carrera updatedCarrera = carreraService.updateCarrera(id, carreraDetails);
            return ResponseEntity.ok(updatedCarrera);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrera(@PathVariable Long id) {
        carreraService.deleteCarrera(id);
        return ResponseEntity.noContent().build();
    }
}

