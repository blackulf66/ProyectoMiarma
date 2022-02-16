package com.salesianostriana.dam.service;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.dto.post.PostDtoConverter;
import com.salesianostriana.dam.exception.FileNotFoundException;
import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.repository.PostRepository;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.service.StorageService;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.repositorys.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;
    private final UserEntityRepository userEntityRepository;

    public Post save(CreatePostDto createPostDto, MultipartFile file ) throws IOException {

        storageService.scaleImage(file , 100);

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return postRepository.save(Post.builder()
                        .titulo(createPostDto.getTitulo())
                        .texto(createPostDto.getTexto())
                        .postEnum(createPostDto.getPostEnum())
                        .imagen(uri)
                        .user(createPostDto.getUser())
                        .build());
    }

    public void deletePost(Long id) throws Exception {
        Optional<Post> data = postRepository.findById(id);
        String name = StringUtils.cleanPath(String.valueOf(data.get().getImagen()))
                .replace("http://localhost:8080/download", "");
        Path path = storageService.load(name);
        String filename = StringUtils.cleanPath(String.valueOf(path))
                .replace("http://localhost:8080/download", "");

        storageService.deleteFile(filename);
        postRepository.deleteById(id);
    }

    public Optional<GetPostDto> updatePost (Long id, CreatePostDto p, MultipartFile file) throws Exception {

            Optional<Post> data = postRepository.findById(id);
            String name = StringUtils.cleanPath(String.valueOf(data.get().getImagen())).replace("http://localhost:8080/download", "");
            Path pa = storageService.load(name);
            String filename = StringUtils.cleanPath(String.valueOf(pa)).replace("http://localhost:8080/download", "");
            ;
            storageService.deleteFile(filename);

            String or = storageService.storeOr(file);
            String newFilename = storageService.storePost(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(newFilename)
                    .toUriString();

            return data.map(m -> {
                m.setTitulo(p.getTitulo());
                m.setTexto(p.getTexto());
                m.setImagen(uri);
                postRepository.save(m);
                return postDtoConverter.postToGetPostDto(m);
            });
        }

    public List<Post> findByPostEnum(PostEnum postEnum) {
        return postRepository.findAll();
    }

    }


