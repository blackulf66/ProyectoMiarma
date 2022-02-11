package com.salesianostriana.dam.users.controllers;


import com.salesianostriana.dam.dto.GetUsuarioDto;
import com.salesianostriana.dam.dto.UsuarioDtoConverter;
import com.salesianostriana.dam.users.dtos.CreateUserDto;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import com.salesianostriana.dam.users.dtos.UserDtoConverter;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.models.UserRole;
import com.salesianostriana.dam.users.services.UserEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = "Los controladores de propietarios")
@CrossOrigin
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final UserEntityService services;
    private final UsuarioDtoConverter dtoConverter;
    }

/*
    @Operation(summary = "publica un usuario propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha publicado en usuario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el usuario",
                    content = @Content),})

    @PostMapping("/auth/register/user")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto nuevoUser) {
        UserEntity saved = userEntityService.saveprop(nuevoUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @Operation(summary = "publica un usuario gestor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha publicado en usuario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el usuario",
                    content = @Content),})

    @PostMapping("/auth/register/gestor")
    public ResponseEntity<GetUserDto> nuevogestor(@RequestBody CreateUserDto nuevoGestor) {
        UserEntity saved = userEntityService.savegestor(nuevoGestor);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }

    @Operation(summary = "publica un usuario administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha publicado en usuario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el usuario",
                    content = @Content),})

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> nuevoadmin(@RequestBody CreateUserDto nuevoAdmin) {
        UserEntity saved = userEntityService.saveadmin(nuevoAdmin);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));

    }


    @Operation(summary = "obtienes la lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado los usuarios",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado los usuarios",
                    content = @Content),})

    @GetMapping("/alluser/")
    public ResponseEntity<Page<GetUsuarioDto>> findAll(@PageableDefault(size=10, page=0) Pageable pageable, HttpServletRequest request){
        Page<UserEntity> data= services.findAll(pageable);
        if (data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            Page<GetUsuarioDto> result=
                    data.map(dtoConverter::usuarioToGetUsuarioDto);

            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

            return ResponseEntity.ok().header("Link" ,
                    paginationLinksUtils.createLinkHeader(result , uriBuilder)).body(result);
        }
    }


    @Operation(summary = "obtiene los usuarios propietarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "se han encontrado",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado",
                    content = @Content),})

    @GetMapping("/user/")
    public ResponseEntity<List<GetUserDto>> findAll(){
        List<UserEntity> prop = userEntityService.loadUserRol(UserRole.PROPIETARIO);

        if(prop.isEmpty()){
            return  ResponseEntity.notFound().build();

        }else{
            List<UserEntity> userprop = prop.stream().collect(Collectors.toList());
            List<GetUserDto> list = userprop.stream().map(userDtoConverter::convertUserEntityToGetUserDto).collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }


    @Operation(summary = "obtiene a un usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el usuario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el usuario",
                    content = @Content),})

    @GetMapping("/user/{id}")
    public ResponseEntity<GetUsuarioDto> findById(@PathVariable UUID id, @AuthenticationPrincipal UserEntity userEntity) {
        Optional<UserEntity> propietarioBuscado = services.findById(id);

        if(propietarioBuscado.isEmpty())
            return ResponseEntity.notFound().build();
        else if(userEntity.getRole().equals(UserRole.ADMIN) && userEntity.getRole().equals(UserRole.PROPIETARIO) && userEntity.getEmail().equals(userEntity.getEmail()))
            return ResponseEntity.ok().body(dtoConverter.usuarioToGetUsuarioDto(propietarioBuscado.get()));
         else
            return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "borra a un usuario por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el usuario",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado el usuario",
                    content = @Content),})

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id ,@AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRole().equals(UserRole.ADMIN) ||id.equals(userEntity.getId()))
        services.deleteById(id);
        return ResponseEntity.noContent().build();
        }

    }*/


