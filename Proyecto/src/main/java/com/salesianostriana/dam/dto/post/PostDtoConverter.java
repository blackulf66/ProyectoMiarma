package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {
    public CreatePostDto createPostDtoToPost(CreatePostDto c , UserEntity usuario){
        return CreatePostDto.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .imagen(c.getImagen())
                .postEnum(c.getPostEnum())
                .texto(c.getTexto())
                .username(usuario.getNick())
                .build();
    }

    public GetPostDto postToGetPostDto(Post p){
        return GetPostDto
                .builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .imagen(p.getImagen())
                .postEnum(p.getPostEnum())
                .username(p.getUser().getNick())
                .build();
    }

}
