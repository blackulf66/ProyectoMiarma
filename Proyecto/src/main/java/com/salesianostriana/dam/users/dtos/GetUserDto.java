package com.salesianostriana.dam.users.dtos;

import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.models.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String avatar;
    private String nick;
    private String email;
    private LocalDate fecha;
    private boolean perfilPrivado;
    private List<UserEntity> following;

}
