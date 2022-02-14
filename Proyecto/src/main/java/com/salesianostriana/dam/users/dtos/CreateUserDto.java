package com.salesianostriana.dam.users.dtos;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String avatar;
    private String nick;
    private String email;
    private String password;
    private Date fecha;
    private boolean perfilPrivado;

}
