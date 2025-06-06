package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearDenunciaDTO {
    private String titulo;
    private String descripcion;
    private List<Long> categoriaIds;
}
