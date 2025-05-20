package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.EstadoDTO;
import co.ufps.pdau.model.Estado;
import co.ufps.pdau.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // Crear el nuevo estado con una lista vacía de denuncias y asignar directamente la lista de Longs
        Estado nuevoEstado = new Estado(
                null,
                estadoDTO.getNombre(),
                estadoDTO.getDescripcion(),
                new ArrayList<>(), // Lista vacía de denuncias
                estadoDTO.getSiguientes() // Asignar directamente la lista de Longs
        );

        return estadoService.createEstado(nuevoEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> updateEstado(@PathVariable Long id, @RequestBody EstadoDTO estadoDTO) {
        try {
            // Verificar si el estado existe
            Estado estadoActual = estadoService.getEstadoById(id)
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + id));

            // Actualizar los datos del estado
            estadoActual.setNombre(estadoDTO.getNombre());
            estadoActual.setDescripcion(estadoDTO.getDescripcion());
            estadoActual.setSiguientes(estadoDTO.getSiguientes()); // Asignar directamente la lista de Longs

            // Guardar el estado actualizado
            Estado savedEstado = estadoService.updateEstado(id, estadoActual);
            return ResponseEntity.ok(savedEstado);
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