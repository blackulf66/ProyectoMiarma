package com.salesianostriana.dam.dto.peticion;


import lombok.*;

import javax.persistence.Lob;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateSeguimientoDTO {

    @Lob
    private String texto;
}
