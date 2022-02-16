package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.exception.FileNotFoundException;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.repository.PostRepository;
import com.salesianostriana.dam.service.PostService;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService Pservice;
    private final PostRepository postRepository;


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost  ,
                                    @RequestParam UserEntity usuario) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Pservice.save(newPost, file, usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<GetPostDto>> updatePublicacion(@PathVariable Long id, @RequestPart("post") CreatePostDto updatePost, @RequestPart("file") MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Pservice.updatePost(id, updatePost, file));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) throws Exception {
        if (id.equals(null)){
            throw new FileNotFoundException("no se encuentra el archivo");
        }else{
            Pservice.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @GetMapping("/public")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(postRepository.findByPostEnum(PostEnum.PUBLICO));
    }

}
