package com.salesianostriana.dam.users.controllers;


import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.security.dto.JwtUserResponse;
import com.salesianostriana.dam.security.dto.LoginDto;
import com.salesianostriana.dam.security.jwt.JwtProvider;
import com.salesianostriana.dam.service.FileSystemStorageService;
import com.salesianostriana.dam.users.dtos.CreateUserDto;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import com.salesianostriana.dam.users.dtos.UserDtoConverter;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.services.UserEntityService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Los controladores de propietarios")
@CrossOrigin
@RequestMapping("/")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final FileSystemStorageService fileSystemStorageService;

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestPart CreateUserDto nuevoUser, @RequestPart MultipartFile file) throws IOException {
        UserEntity saved = userEntityService.saveuser(nuevoUser , file);
        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<GetUserDto> finduserById(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user){
        Optional<UserEntity> uOptional = userEntityService.findById(id);

        if(uOptional.isEmpty()){
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok().body(userDtoConverter.convertUserEntityToGetUserDto(uOptional.get()));
        }

    }
    @PutMapping("user/{id}")
    public ResponseEntity<Optional<GetUserDto>> updatePublicacion(@PathVariable UUID id, @RequestPart("user") CreateUserDto updateUser, @RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserEntity user) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userEntityService.updateUser(id, updateUser, file , user));

    }
}




