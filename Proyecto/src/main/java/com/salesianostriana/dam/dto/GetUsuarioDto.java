package com.salesianostriana.dam.dto;

import com.dam.grupo2.realstate.users.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUsuarioDto {
    private UUID id;
    private UserRole rol ;
    private String nombre , apellidos ,direccion ,email ,telefono ,avatar, password ;

}
