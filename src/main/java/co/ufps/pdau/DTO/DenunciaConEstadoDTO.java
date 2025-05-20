package co.ufps.pdau.DTO;

import co.ufps.pdau.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DenunciaConEstadoDTO {
    private String titulo;
    private String descripcion;
    private Estado estado;
}