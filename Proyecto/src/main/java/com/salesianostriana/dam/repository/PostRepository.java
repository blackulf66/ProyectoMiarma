package com.salesianostriana.dam.repository;

import com.salesianostriana.dam.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends
        JpaRepository<Post, Long> {
}
