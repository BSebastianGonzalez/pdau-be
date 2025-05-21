package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivarDenunciaDTO {
    private Long idDenuncia;
    private Long idAdministrador;
    private boolean archivar;
    private String justificacion;
}
