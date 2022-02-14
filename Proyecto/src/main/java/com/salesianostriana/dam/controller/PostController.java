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

    private final PostService service;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newProduct) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.save(newProduct, file));
    }

    @GetMapping("/post/public")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.findAll());
    }

}
