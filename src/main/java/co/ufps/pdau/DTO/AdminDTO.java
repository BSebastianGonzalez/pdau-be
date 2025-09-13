package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private Long telefono;
    private String direccion;
    private String correo;
    private String contrasenia;
    private String role;
}
