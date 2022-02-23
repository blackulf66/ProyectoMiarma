package com.salesianostriana.dam.model;

import com.salesianostriana.dam.users.models.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Seguimiento implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String peticion;

    @ManyToOne
    @JoinColumn(name = "destino_usuario")
    private UserEntity destino;

    @ManyToOne
    @JoinColumn(name = "origen_usuario")
    private UserEntity origen;

    @PreRemove
    public void nullearDestinatarios(){
        origen.setFollowing(null);
    }

}
