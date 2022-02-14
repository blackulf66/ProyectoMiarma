package com.salesianostriana.dam.service;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    Post save(CreatePostDto createPostDto, MultipartFile file);

    List<Post> findAll();
}
