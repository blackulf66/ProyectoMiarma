package com.salesianostriana.dam.security.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String email;
    private String nick;
    private String avatar;
    private String role;
    private String token;
    private Date fecha;
    private boolean perfilPrivado;

}