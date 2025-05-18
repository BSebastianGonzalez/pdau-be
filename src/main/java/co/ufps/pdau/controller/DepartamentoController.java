package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.DepartamentoDTO;
import co.ufps.pdau.model.Departamento;
import co.ufps.pdau.service.DepartamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> getAllDepartamentos() {
        return departamentoService.getAllDepartamentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> getDepartamentoById(@PathVariable Long id) {
        return departamentoService.getDepartamentoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Departamento> createDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        Departamento createdDepartamento = departamentoService.createDepartamento(new Departamento(null, departamentoDTO.getNombre(), departamentoDTO.getDescripcion(), null));
        return ResponseEntity.status(201).body(createdDepartamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> updateDepartamento(@PathVariable Long id, @RequestBody Departamento departamentoDetails) {
        try {
            Departamento updatedDepartamento = departamentoService.updateDepartamento(id, departamentoDetails);
            return ResponseEntity.ok(updatedDepartamento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        departamentoService.deleteDepartamento(id);
        return ResponseEntity.noContent().build();
    }
}