package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambioEstadoDTO {
    private Long idDenuncia;
    private Long idAdministrador;
    private Long idEstado;
    private String justificacion;
}
