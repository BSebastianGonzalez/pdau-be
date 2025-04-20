package co.ufps.pdau.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraRequestDTO {
    private Long id;
    private String nombre;
    private Long facultadId;
}
