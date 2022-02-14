package com.salesianostriana.dam.users.dtos;

import com.salesianostriana.dam.users.models.UserRole;
import lombok.*;

import java.util.Date;
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
    private Date fecha;
    private boolean perfilPrivado;

}
