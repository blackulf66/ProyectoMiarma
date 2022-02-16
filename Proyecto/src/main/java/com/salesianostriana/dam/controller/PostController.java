package com.salesianostriana.dam.controller;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.dto.post.PostDtoConverter;
import com.salesianostriana.dam.exception.FileNotFoundException;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.repository.PostRepository;
import com.salesianostriana.dam.service.PostService;
import com.salesianostriana.dam.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService Pservice;
    private final PostRepository postRepository;
    private final PostDtoConverter postDtoConverter;


    @PostMapping("/")
    public ResponseEntity<?> create(@RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto newPost,
                                    @AuthenticationPrincipal UserEntity user) throws IOException {

        Post postCreated = Pservice.save(newPost, file , user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postDtoConverter.postToGetPostDto(postCreated , user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<GetPostDto>> updatePublicacion(@PathVariable Long id, @RequestPart("post") CreatePostDto updatePost, @RequestPart("file") MultipartFile file, @AuthenticationPrincipal UserEntity user) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Pservice.updatePost(id, updatePost, file , user));

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



    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto> findpostbyID(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){
        Optional<Post> postOptional = Pservice.findPostById(id);

        if(postOptional.isEmpty()){
            return ResponseEntity.notFound().build();

        }else{
            return ResponseEntity.ok().body(postDtoConverter.postToGetPostDto(postOptional.get(),user));
        }

        }

       /* @GetMapping("/nick")
        public ResponseEntity<List<GetPostDto>> finduserbynickname(@RequestParam(value = "nickname") String nickname){

        if(nickname.isBlank()){
            return ResponseEntity.notFound().build();
        }else{
           return ResponseEntity.ok().body(Pservice.listPostDto(nickname));
        }

        }*/




}