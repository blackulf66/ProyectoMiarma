package com.salesianostriana.dam.model;


import com.salesianostriana.dam.users.models.UserEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String texto;

    private String imagen;

    private PostEnum postEnum;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private UserEntity user;






}
