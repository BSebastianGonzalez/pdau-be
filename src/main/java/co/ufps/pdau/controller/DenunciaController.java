package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.CrearDenunciaDTO;
import co.ufps.pdau.DTO.DenunciaConEstadoDTO;
import co.ufps.pdau.model.Categoria;
import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.model.Estado;
import co.ufps.pdau.repository.CategoriaRepository;
import co.ufps.pdau.service.DenunciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/denuncias")
@RequiredArgsConstructor
public class DenunciaController {
    private final DenunciaService denunciaService;
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
    public ResponseEntity<Map<String, String>> crearDenuncia(@RequestBody CrearDenunciaDTO dto) {
        // Buscar categorías por ID
        List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriaIds());


        // Crear la denuncia
        Denuncia denuncia = new Denuncia();
        denuncia.setTitulo(dto.getTitulo());
        denuncia.setDescripcion(dto.getDescripcion());
        denuncia.setCategorias(categorias);  // Asignar las categorías
        denuncia.setArchivado(false);
        denuncia.setFechaCreacion(new java.util.Date());
        denuncia.setDepartamento(null);

        Estado estado = new Estado();
        estado.setId(1L); // ID del estado predeterminado
        denuncia.setEstado(estado);

        // Guardar la denuncia y obtener el token
        Denuncia savedDenuncia = denunciaService.createDenuncia(denuncia);

        // Crear la respuesta con el token
        Map<String, String> response = new HashMap<>();
        response.put("token", savedDenuncia.getTokenSeguimiento());
        response.put("id", String.valueOf(savedDenuncia.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @GetMapping("/{id}/token")
    public ResponseEntity<String> getTokenByDenunciaId(@PathVariable Long id) {
        String token = denunciaService.getTokenByDenunciaId(id);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/token/{token}")
    public ResponseEntity<DenunciaConEstadoDTO> getDenunciaByToken(@PathVariable String token) {
        return denunciaService.getDenunciaByToken(token)
                .map(denuncia -> {
                    DenunciaConEstadoDTO dto = new DenunciaConEstadoDTO(
                            denuncia.getTitulo(),
                            denuncia.getDescripcion(),
                            denuncia.getEstado().getNombre()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/archivadas")
    public List<Denuncia> getDenunciasArchivadas() {
        return denunciaService.getDenunciasArchivadas();
    }

    @GetMapping("/no-archivadas")
    public List<Denuncia> getDenunciasNoArchivadas() {
        return denunciaService.getDenunciasNoArchivadas();
    }

    @PutMapping("/{id}/toggle-archivado")
    public ResponseEntity<Denuncia> toggleArchivado(@PathVariable Long id) {
        try {
            Denuncia updatedDenuncia = denunciaService.toggleArchivado(id);
            return ResponseEntity.ok(updatedDenuncia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idDenuncia}/departamento/{idDepartamento}")
    public ResponseEntity<Denuncia> asignarDenunciaADepartamento(
            @PathVariable Long idDenuncia, @PathVariable Long idDepartamento) {
        try {
            Denuncia denunciaActualizada = denunciaService.asignarDenunciaADepartamento(idDenuncia, idDepartamento);
            return ResponseEntity.ok(denunciaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/departamento/{idDepartamento}")
    public ResponseEntity<List<Denuncia>> getDenunciasByDepartamento(@PathVariable Long idDepartamento) {
        List<Denuncia> denuncias = denunciaService.getDenunciasByDepartamento(idDepartamento);
        return ResponseEntity.ok(denuncias);
    }
}

