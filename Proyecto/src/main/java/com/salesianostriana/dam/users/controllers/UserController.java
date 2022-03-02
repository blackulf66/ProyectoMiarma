package com.salesianostriana.dam.users.controllers;


import com.salesianostriana.dam.dto.peticion.CreateSeguimientoDTO;
import com.salesianostriana.dam.dto.peticion.GetSeguimientoDTO;
import com.salesianostriana.dam.dto.peticion.SeguimientoDtoConverter;
import com.salesianostriana.dam.exception.UserException;
import com.salesianostriana.dam.model.Seguimiento;
import com.salesianostriana.dam.security.jwt.JwtProvider;
import com.salesianostriana.dam.service.FileSystemStorageService;
import com.salesianostriana.dam.service.SeguimientoService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(tags = "Los controladores de user")
@CrossOrigin
@RequestMapping("/")
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final FileSystemStorageService fileSystemStorageService;
    private final SeguimientoService seguimientoService;

    @PostMapping("auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestParam("nick") String nick , @RequestParam("email") String email,@RequestParam("password") String password,@RequestParam("perfilProvado") Boolean perfilProvado, @RequestPart MultipartFile file) throws IOException {

        CreateUserDto createUserDto = CreateUserDto.builder()
                .nick(nick)
                .email(email)
                .password(password)
                .perfilPrivado(perfilProvado)
                .build();

        UserEntity saved = userEntityService.saveuser(createUserDto , file);
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
    public ResponseEntity<Optional<GetUserDto>> updatePublicacion(@PathVariable UUID id, @RequestPart("user") CreateUserDto updateUser, @RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserEntity user) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userEntityService.updateUser(id, updateUser, file , user));

    }

    @GetMapping("follow/list")
    public ResponseEntity<List<GetSeguimientoDTO>> listadoSeguimientos(){
        if (seguimientoService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetSeguimientoDTO> list = seguimientoService.findAll().stream()
                    .map(SeguimientoDtoConverter::SeguimientoToGetSeguimientoDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }
    @PutMapping("profile/me")
    public ResponseEntity<Optional<GetUserDto>> actualizarPerfil (@AuthenticationPrincipal UserEntity userEntity, @RequestPart("user") CreateUserDto createUserDto, @RequestPart("file")MultipartFile file) throws Exception {

        return ResponseEntity.ok(userEntityService.actualizarPerfil(userEntity, createUserDto, file));
    }
    @PostMapping("follow/{nick}")
    public ResponseEntity<GetSeguimientoDTO> realizarfollow (@PathVariable String nick, @RequestPart("seguimiento") CreateSeguimientoDTO createSeguimientoDto, @AuthenticationPrincipal UserEntity user){

        Seguimiento seguimiento = userEntityService.sendSeguimiento(nick, createSeguimientoDto, user);

        return  ResponseEntity.status(HttpStatus.CREATED).body(SeguimientoDtoConverter.SeguimientoToGetSeguimientoDto(seguimiento));
    }
    @PostMapping("follow/accept/{id}")
    public ResponseEntity<?> acceptfollow(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        if (id.equals(null)){
            throw new UserException("No existe id del seguimiento");
        }else {
            userEntityService.aceptarSeguimiento(id, userEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }
    @PostMapping("follow/decline/{id}")
    public ResponseEntity<?> declinefollow(@PathVariable Long id){
        if (id.equals(null)){
            throw new UserException("No existe id del seguimiento");
        }else {
            userEntityService.rechazarSeguimiento(id);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

    }

}




