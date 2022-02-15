package com.salesianostriana.dam.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue
    private UUID id;

    private String titulo;

    private String texto;

    private String imagen;

    private Boolean privada;

}
