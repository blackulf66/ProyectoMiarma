package com.salesianostriana.dam.users.dtos;

import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .nick(user.getNick())
                .email(user.getEmail())
                .fecha(user.getFecha())
                .perfilPrivado(user.isPerfilprivado())
                .following(user.getFollowing())
                .posts(user.getPosts())
                .build();

    }

}

