package com.salesianostriana.dam.users.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String avatar;
    private String fullname;
    private String email;
    private String password;
    private String password2;

}
