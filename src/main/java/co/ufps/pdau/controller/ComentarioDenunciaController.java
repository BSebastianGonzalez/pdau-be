package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.ComentarioDenunciaDTO;
import co.ufps.pdau.model.ComentarioDenuncia;
import co.ufps.pdau.service.AdminService;
import co.ufps.pdau.service.ComentarioDenunciaService;
import co.ufps.pdau.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioDenunciaController {

    @Autowired
    private ComentarioDenunciaService comentarioDenunciaService;

    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<ComentarioDenuncia>> getAllComentariosDenuncia() {
        List<ComentarioDenuncia> comentarios = comentarioDenunciaService.getAllComentariosDenuncia();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioDenuncia> getComentarioDenunciaById(@PathVariable Long id) {
        return comentarioDenunciaService.getComentarioDenunciaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/denuncia/{idDenuncia}")
    public ResponseEntity<List<ComentarioDenuncia>> getComentariosByDenunciaId(@PathVariable Long idDenuncia) {
        List<ComentarioDenuncia> comentarios = comentarioDenunciaService.getComentariosByDenunciaId(idDenuncia);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping
    public ResponseEntity<ComentarioDenuncia> saveComentarioDenuncia(@RequestBody ComentarioDenunciaDTO comentarioDenunciaDTO) {
        try {
            ComentarioDenuncia comentarioDenuncia = new ComentarioDenuncia();
            comentarioDenuncia.setComentario(comentarioDenunciaDTO.getComentario());
            comentarioDenuncia.setDenuncia(denunciaService.getDenunciaById(comentarioDenunciaDTO.getIdDenuncia())
                    .orElseThrow(() -> new RuntimeException("Denuncia no encontrada con ID: " + comentarioDenunciaDTO.getIdDenuncia())));
            comentarioDenuncia.setAdmin(adminService.getAdminById(comentarioDenunciaDTO.getIdAdmin())
                    .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + comentarioDenunciaDTO.getIdAdmin())));
            comentarioDenuncia.setFechaComentario(new java.util.Date());

            ComentarioDenuncia savedComentario = comentarioDenunciaService.saveComentarioDenuncia(comentarioDenuncia);
            return ResponseEntity.ok(savedComentario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentarioDenuncia(@PathVariable Long id) {
        if (comentarioDenunciaService.getComentarioDenunciaById(id).isPresent()) {
            comentarioDenunciaService.deleteComentarioDenuncia(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<ComentarioDenuncia>> getByAdminId(@PathVariable Long adminId) {
        List<ComentarioDenuncia> lista = comentarioDenunciaService.getComentariosByAdminId(adminId);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
}