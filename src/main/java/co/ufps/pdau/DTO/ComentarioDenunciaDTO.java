package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDenunciaDTO {
    private Long idAdmin;
    private Long idDenuncia;
    private String comentario;
}
