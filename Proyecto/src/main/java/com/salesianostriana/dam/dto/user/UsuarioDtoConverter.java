package com.salesianostriana.dam.dto.user;

import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDtoConverter {
    public UserEntity createUsuarioDtoTousuario(CreateUsuarioDto c) {
        return new UserEntity(
               c.getId(),
               c.getEmail(),
               c.getPassword(),
               c.getAvatar(),
               c.getFecha(),
               c.getApellidos(),
               c.getRol(),
               c.isPerfilPrivado()
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