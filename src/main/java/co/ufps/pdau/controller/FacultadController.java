package co.ufps.pdau.controller;

import co.ufps.pdau.model.Facultad;
import co.ufps.pdau.service.FacultadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facultades")
@RequiredArgsConstructor
public class FacultadController {
    private final FacultadService facultadService;

    @GetMapping("/list")
    public List<Facultad> getAllFacultades() {
        return facultadService.getAllFacultades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facultad> getFacultadById(@PathVariable Long id) {
        return facultadService.getFacultadById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Facultad createFacultad(@RequestBody Facultad facultad) {
        return facultadService.createFacultad(facultad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facultad> updateFacultad(@PathVariable Long id, @RequestBody Facultad facultadDetails) {
        try {
            Facultad updatedFacultad = facultadService.updateFacultad(id, facultadDetails);
            return ResponseEntity.ok(updatedFacultad);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        facultadService.deleteFacultad(id);
        return ResponseEntity.noContent().build();
    }
}

