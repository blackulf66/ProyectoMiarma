package com.salesianostriana.dam.security;


import com.salesianostriana.dam.dto.post.PostDtoConverter;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.security.dto.JwtUserResponse;
import com.salesianostriana.dam.security.dto.LoginDto;
import com.salesianostriana.dam.security.jwt.JwtProvider;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PostDtoConverter dto;

    String jwt="";

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwt = jwtProvider.generateToken(authentication);


        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));

    }

    @GetMapping("me")
    public ResponseEntity<?> tusdatos(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, jwt));
    }

    private JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .username(user.getUsername())
                .token(jwt)
                .nick(user.getNick())
                .posts(user.getPosts().stream().map(dto::postToGetPostDto).collect(Collectors.toList()))
                .build();
    }

}