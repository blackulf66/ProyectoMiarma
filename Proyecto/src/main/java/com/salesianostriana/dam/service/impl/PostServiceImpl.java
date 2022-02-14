package com.salesianostriana.dam.service.impl;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.repository.PostRepository;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.service.PostService;
import com.salesianostriana.dam.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StorageService storageService;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file) {

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return postRepository.save(Post.builder()
                        .nombre(createPostDto.getNombre())
                        .imagen(uri)
                .build());
    }


    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }


}
