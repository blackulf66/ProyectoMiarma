package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.PostEnum;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CreatePostDto {

    private Long id;

    private String titulo,texto;

    private String imagen;

    private PostEnum postEnum;
}
