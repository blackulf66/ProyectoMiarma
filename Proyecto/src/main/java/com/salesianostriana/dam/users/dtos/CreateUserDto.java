package com.salesianostriana.dam.users.dtos;


import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
    private LocalDate fecha;
    private boolean perfilPrivado;
    private List<UserEntity> following;
    private List<Post> posts;

}
