package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.PostEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostDto {
    private UUID id;

    private String titulo,texto;

    private String imagen;

    private PostEnum postEnum;


}
