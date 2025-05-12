package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.EstadoDTO;
import co.ufps.pdau.model.Estado;
import co.ufps.pdau.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {
    private final EstadoService estadoService;

    @GetMapping("/list")
    public List<Estado> getAllEstados() {
        return estadoService.getAllEstados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> getEstadoById(@PathVariable Long id) {
        return estadoService.getEstadoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estado createEstado(@RequestBody EstadoDTO estadoDTO) {
        return estadoService.createEstado(new Estado(null, estadoDTO.getNombre(), estadoDTO.getDescripcion(), new ArrayList<>()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable Long id, @RequestBody EstadoDTO estadoDTO) {
        try {
            Estado updatedEstado = estadoService.updateEstado(id, new Estado(null, estadoDTO.getNombre(), estadoDTO.getDescripcion(), new ArrayList<>()));
            return ResponseEntity.ok(updatedEstado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        estadoService.deleteEstado(id);
        return ResponseEntity.noContent().build();
    }
}