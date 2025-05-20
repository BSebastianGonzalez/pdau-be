package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.ArchivarDenunciaDTO;
import co.ufps.pdau.model.ArchivarDenuncia;
import co.ufps.pdau.model.Admin;
import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.service.ArchivarDenunciaService;
import co.ufps.pdau.service.AdminService;
import co.ufps.pdau.service.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/archivar-denuncias")
public class ArchivarDenunciaController {

    @Autowired
    private ArchivarDenunciaService archivarDenunciaService;

    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<ArchivarDenuncia>> getAllArchivarDenuncias() {
        List<ArchivarDenuncia> archivarDenuncias = archivarDenunciaService.getAllArchivarDenuncias();
        return ResponseEntity.ok(archivarDenuncias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchivarDenuncia> getArchivarDenunciaById(@PathVariable Long id) {
        return archivarDenunciaService.getArchivarDenunciaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArchivarDenuncia> registrarArchivarDenuncia(@RequestBody ArchivarDenunciaDTO archivarDenunciaDTO) {
        try {
            Denuncia denuncia = denunciaService.getDenunciaById(archivarDenunciaDTO.getIdDenuncia())
                    .orElseThrow(() -> new RuntimeException("Denuncia no encontrada con ID: " + archivarDenunciaDTO.getIdDenuncia()));

            Admin admin = adminService.getAdminById(archivarDenunciaDTO.getIdAdministrador())
                    .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + archivarDenunciaDTO.getIdAdministrador()));

            ArchivarDenuncia archivarDenuncia = new ArchivarDenuncia();
            archivarDenuncia.setDenuncia(denuncia);
            archivarDenuncia.setAdmin(admin);
            archivarDenuncia.setArchivar(archivarDenunciaDTO.isArchivar());
            archivarDenuncia.setJustificacion(archivarDenunciaDTO.getJustificacion());
            archivarDenuncia.setFechaArchivar(new Date());

            ArchivarDenuncia archivarDenunciaGuardada = archivarDenunciaService.saveArchivarDenuncia(archivarDenuncia);

            denuncia.setArchivado(archivarDenunciaDTO.isArchivar());
            denuncia.setFechaArchivado(archivarDenunciaDTO.isArchivar() ? new Date() : null);
            denunciaService.updateDenuncia(denuncia.getId(), denuncia);

            return ResponseEntity.ok(archivarDenunciaGuardada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArchivarDenuncia(@PathVariable Long id) {
        if (archivarDenunciaService.getArchivarDenunciaById(id).isPresent()) {
            archivarDenunciaService.deleteArchivarDenuncia(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
