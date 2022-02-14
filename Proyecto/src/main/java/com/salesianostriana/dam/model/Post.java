package com.salesianostriana.dam.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    private String imagenmini;

}
