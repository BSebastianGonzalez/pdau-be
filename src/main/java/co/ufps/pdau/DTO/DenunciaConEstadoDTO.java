package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DenunciaConEstadoDTO {
    private String titulo;
    private String descripcion;
    private String estadoNombre;
}