package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {
    public Post createPostDtoToPost(CreatePostDto c){



        return new Post(
                c.getId(),
                c.getTitulo(),
                c.getTexto(),
                c.getImagen(),
                c.getPostEnum(),
                c.getUser()
        );
    }

    public GetPostDto postToGetPostDto(Post p , UserEntity user){

        return GetPostDto
                .builder()
                .id(p.getId())
                .titulo(p.getTitulo())
                .texto(p.getTexto())
                .imagen(p.getImagen())
                .postEnum(p.getPostEnum())
                .user(user)
                .build();
    }
}
