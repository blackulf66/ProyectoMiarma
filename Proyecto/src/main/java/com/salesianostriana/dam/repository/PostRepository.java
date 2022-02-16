package com.salesianostriana.dam.repository;

import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.PostEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostEnum(PostEnum xmen);

   /* List<Post> findUserByNickname(String nickname);*/



}
