package com.salesianostriana.dam.repository;

import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostEnum(PostEnum postEnum);

    @Query("""
            SELECT p FROM Post p
            WHERE p.postEnum = :postEnum
            AND p.user.nick = :nick
            """)
    List<Post> findPostUserByNick(@Param("postEnum") PostEnum postEnum,@Param("nick") String nick);

    @EntityGraph(value = "Post-UserEntity")
    List<Post> findByUserNick(String nick);



}
