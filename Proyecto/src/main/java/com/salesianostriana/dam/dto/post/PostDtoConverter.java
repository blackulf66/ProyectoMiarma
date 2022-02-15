package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {
    public Post createPostDtoToPost(CreatePostDto c){
        return new Post(
                c.getId(),
                c.getTitulo(),
                c.getTexto(),
                c.getImagen(),
                c.isPrivada()
        );
    }

    public GetPostDto postToGetPostDto(Post p){
        return GetPostDto
                .builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .imagen(p.getImagen())
                .privada(p.getPrivada())
                .build();
    }
}
