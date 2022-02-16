package com.salesianostriana.dam.model;


import com.salesianostriana.dam.users.models.UserEntity;
import lombok.*;
import org.apache.catalina.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String titulo;

    private String texto;

    private String imagen;

    private PostEnum postEnum;


}
