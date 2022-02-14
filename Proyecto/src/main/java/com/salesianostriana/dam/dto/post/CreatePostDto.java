package com.salesianostriana.dam.dto.post;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePostDto {

    private String nombre,titulo,texto;

    private String foto;

}
