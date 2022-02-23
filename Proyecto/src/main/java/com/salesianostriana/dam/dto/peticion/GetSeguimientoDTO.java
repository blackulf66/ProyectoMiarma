package com.salesianostriana.dam.dto.peticion;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetSeguimientoDTO {

    private Long id;
    private String texto;
    private String destino;
}
