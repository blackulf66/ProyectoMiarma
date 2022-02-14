package com.salesianostriana.dam.dto.user;

import com.salesianostriana.dam.users.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUsuarioDto {

    private UUID id;
    private UserRole rol;
    private String nombre ,apellidos ,email ,avatar, password;
    private Date fecha;
    private boolean perfilPrivado;

}
