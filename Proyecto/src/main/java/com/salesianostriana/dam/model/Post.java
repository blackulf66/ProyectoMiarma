package com.salesianostriana.dam.model;


import com.salesianostriana.dam.users.models.UserEntity;
import lombok.*;
import javax.persistence.*;

@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Post-UserEntity",
                attributeNodes = { @NamedAttributeNode("user") })
})
@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter

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
