package co.ufps.pdau.controller;

import co.ufps.pdau.DTO.AdminDTO;
import co.ufps.pdau.DTO.LoginRequest;
import co.ufps.pdau.DTO.LoginResponse;
import co.ufps.pdau.model.Admin;
import co.ufps.pdau.model.Role;
import co.ufps.pdau.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/list")
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Admin createAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setNombre(adminDTO.getNombre());
        admin.setCedula(adminDTO.getCedula());
        admin.setApellido(adminDTO.getApellido());
        admin.setTelefono(adminDTO.getTelefono());
        admin.setDireccion(adminDTO.getDireccion());
        admin.setCorreo(adminDTO.getCorreo());
        admin.setContrasenia(adminDTO.getContrasenia());
        if (adminDTO.getRole() != null) {
            admin.setRole(Role.valueOf(adminDTO.getRole()));
        }
        return adminService.createAdmin(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        try {
            Admin adminDetails = new Admin();
            adminDetails.setNombre(adminDTO.getNombre());
            adminDetails.setId(id);
            adminDetails.setApellido(adminDTO.getApellido());
            adminDetails.setTelefono(adminDTO.getTelefono());
            adminDetails.setDireccion(adminDTO.getDireccion());
            adminDetails.setCorreo(adminDTO.getCorreo());
            adminDetails.setCedula(adminDTO.getCedula());
            adminDetails.setContrasenia(adminDTO.getContrasenia());
            if (adminDTO.getRole() != null) {
                adminDetails.setRole(Role.valueOf(adminDTO.getRole()));
            }
            Admin updatedAdmin = adminService.updateUser(id, adminDetails);
            return ResponseEntity.ok(updatedAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = adminService.login(request.getCorreo(), request.getContrasenia());
        return ResponseEntity.ok(response);
    }
}
