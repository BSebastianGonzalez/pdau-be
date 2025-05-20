package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.CambioEstadoDTO;
import co.ufps.pdau.model.CambioEstado;
import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.model.Estado;
import co.ufps.pdau.model.Admin;
import co.ufps.pdau.service.CambioEstadoService;
import co.ufps.pdau.service.DenunciaService;
import co.ufps.pdau.service.EstadoService;
import co.ufps.pdau.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cambios-estado")
public class CambioEstadoController {

    @Autowired
    private CambioEstadoService cambioEstadoService;

    @Autowired
    private DenunciaService denunciaService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<CambioEstado>> getAllCambiosEstado() {
        List<CambioEstado> cambiosEstado = cambioEstadoService.getAllCambiosEstado();
        return ResponseEntity.ok(cambiosEstado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CambioEstado> getCambioEstadoById(@PathVariable Long id) {
        return cambioEstadoService.getCambioEstadoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CambioEstado> registrarCambioEstado(@RequestBody CambioEstadoDTO cambioEstadoDTO) {
        try {
            Denuncia denuncia = denunciaService.getDenunciaById(cambioEstadoDTO.getIdDenuncia())
                    .orElseThrow(() -> new RuntimeException("Denuncia no encontrada con ID: " + cambioEstadoDTO.getIdDenuncia()));

            Estado nuevoEstado = estadoService.getEstadoById(cambioEstadoDTO.getIdEstado())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado con ID: " + cambioEstadoDTO.getIdEstado()));

            Admin admin = adminService.getAdminById(cambioEstadoDTO.getIdAdministrador())
                    .orElseThrow(() -> new RuntimeException("Administrador no encontrado con ID: " + cambioEstadoDTO.getIdAdministrador()));

            CambioEstado cambioEstado = new CambioEstado();
            cambioEstado.setDenuncia(denuncia);
            cambioEstado.setEstadoAnterior(denuncia.getEstado());
            cambioEstado.setEstado(nuevoEstado);
            cambioEstado.setAdmin(admin);
            cambioEstado.setJustificacion(cambioEstadoDTO.getJustificacion());
            cambioEstado.setFechaCambio(new Date());

            CambioEstado cambioEstadoGuardado = cambioEstadoService.saveCambioEstado(cambioEstado);

            denuncia.setEstado(nuevoEstado);
            denunciaService.updateDenuncia(denuncia.getId(), denuncia);

            return ResponseEntity.ok(cambioEstadoGuardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCambioEstado(@PathVariable Long id) {
        if (cambioEstadoService.getCambioEstadoById(id).isPresent()) {
            cambioEstadoService.deleteCambioEstado(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/denuncia/{idDenuncia}")
    public ResponseEntity<List<CambioEstado>> getCambiosEstadoByDenunciaId(@PathVariable Long idDenuncia) {
        List<CambioEstado> cambiosEstado = cambioEstadoService.getCambiosEstadoByDenunciaId(idDenuncia);
        if (cambiosEstado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cambiosEstado);
    }
}