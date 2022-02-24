package com.salesianostriana.dam.security.dto;

import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private List<GetPostDto> posts;

}