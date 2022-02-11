package com.salesianostriana.dam.users.dtos;

import com.salesianostriana.dam.users.models.UserRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private UUID id;
    private String avatar;
    private String fullName;
    private String email;
    private UserRole role;


}
