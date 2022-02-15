package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService Pservice;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Pservice.save(newPost, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto updatedPost){

        return ResponseEntity.status(HttpStatus.OK).body(Pservice.save(updatedPost,file));
    }

    @GetMapping("/post/public")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(Pservice.findAll());
    }

}
