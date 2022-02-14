package com.salesianostriana.dam.users.controllers;


import com.salesianostriana.dam.dto.user.GetUsuarioDto;
import com.salesianostriana.dam.dto.user.UsuarioDtoConverter;
import com.salesianostriana.dam.users.dtos.CreateUserDto;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import com.salesianostriana.dam.users.dtos.UserDtoConverter;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.models.UserRole;
import com.salesianostriana.dam.users.services.UserEntityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Los controladores de propietarios")
@CrossOrigin
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final UserEntityService services;
    private final UsuarioDtoConverter dtoConverter;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto nuevoUser) {
        UserEntity saved = userEntityService.saveuser(nuevoUser);
        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<GetUsuarioDto> findById(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Optional<UserEntity> usuarioBuscado = services.findById(id);

        if (usuarioBuscado.isEmpty())
            return ResponseEntity.notFound().build();
        else if (userEntity.getRole().equals(UserRole.USER)  && userEntity.getEmail().equals(userEntity.getEmail()))
            return ResponseEntity.ok().body(dtoConverter.usuarioToGetUsuarioDto(usuarioBuscado.get()));
        else
            return ResponseEntity.badRequest().build();
    }
}




