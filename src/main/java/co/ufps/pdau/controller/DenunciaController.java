package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.CrearDenunciaDTO;
import co.ufps.pdau.model.Carrera;
import co.ufps.pdau.model.Categoria;
import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.model.Facultad;
import co.ufps.pdau.repository.CategoriaRepository;
import co.ufps.pdau.service.CarreraService;
import co.ufps.pdau.service.DenunciaService;
import co.ufps.pdau.service.FacultadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/denuncias")
@RequiredArgsConstructor
public class DenunciaController {
    private final DenunciaService denunciaService;
    private final FacultadService facultadService;
    private final CarreraService carreraService;
    private final CategoriaRepository categoriaRepository;

    @GetMapping("/list")
    public List<Denuncia> getAllDenuncias() {
        return denunciaService.getAllDenuncias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> getDenunciaById(@PathVariable Long id) {
        return denunciaService.getDenunciaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crearDenuncia(@RequestBody CrearDenunciaDTO dto) {

        // Obtener facultad y carrera
        Facultad facultad = facultadService.getFacultadById(dto.getFacultadId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Facultad no encontrada"));

        Carrera carrera = carreraService.getCarreraById(dto.getCarreraId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrera no encontrada"));

        // Buscar categorías por ID
        List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriaIds());

        // Crear la denuncia
        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(dto.getTitulo());
        denuncia.setDescripcion(dto.getDescripcion());
        denuncia.setFacultad(facultad);
        denuncia.setCarrera(carrera);
        denuncia.setCategorias(categorias);  // Asignar las categorías

        denunciaService.createDenuncia(denuncia);

        return ResponseEntity.status(HttpStatus.CREATED).body("Denuncia creada exitosamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> updateDenuncia(@PathVariable Long id, @RequestBody Denuncia denunciaDetails) {
        try {
            Denuncia updatedDenuncia = denunciaService.updateDenuncia(id, denunciaDetails);
            return ResponseEntity.ok(updatedDenuncia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDenuncia(@PathVariable Long id) {
        denunciaService.deleteDenuncia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias/{idCategoria}")
    public List<Denuncia> getDenunciasByCategoria(@PathVariable Long idCategoria) {
        return denunciaService.getDenunciasByCategoria(idCategoria);
    }
}

