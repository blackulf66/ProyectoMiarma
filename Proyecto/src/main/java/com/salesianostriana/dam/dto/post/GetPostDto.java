package com.salesianostriana.dam.dto.post;

import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPostDto {
    private Long id;

    private String titulo,texto;

    private String imagen;

    private PostEnum postEnum;

    private String username;




}
