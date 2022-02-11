package com.salesianostriana.dam.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateProductoDto {

    private String nombre;
    private double pvp;

}
