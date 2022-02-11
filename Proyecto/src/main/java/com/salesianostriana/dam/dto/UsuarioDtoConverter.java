package com.salesianostriana.dam.dto;

import com.dam.grupo2.realstate.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoConverter {
    public UserEntity createUsuarioDtoTousuario(CreateUsuarioDto c) {
        return new UserEntity(
                c.getEmail(),
                c.getPassword(),
                c.getAvatar(),
                c.getApellidos(),
                c.getRol()

        );
    }
    public GetUsuarioDto usuarioToGetUsuarioDto(UserEntity u) {
        return GetUsuarioDto
                .builder()
                .id(u.getId())
                .nombre(u.getPassword())
                .email(u.getEmail())
                .avatar(u.getAvatar())
                .rol(u.getRole())
                .build();
    }
}