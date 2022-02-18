package com.salesianostriana.dam.service;

import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.dto.post.PostDtoConverter;
import com.salesianostriana.dam.exception.FileNotFoundException;
import com.salesianostriana.dam.exception.SingleEntityNotFoundException;
import com.salesianostriana.dam.model.PostEnum;
import com.salesianostriana.dam.repository.PostRepository;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.service.StorageService;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.repositorys.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final StorageService storageService;
    private final PostDtoConverter postDtoConverter;
    private final UserEntityRepository userEntityRepository;

    public Post save(CreatePostDto createPostDto, MultipartFile file ,UserEntity user) throws IOException {

        String filenameOriginal = storageService.store(file);

        String filename = storageService.store(file);

        String extension = StringUtils.getFilenameExtension(filename);

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        BufferedImage escaledImage = storageService.simpleResizer(originalImage,1024);

        OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

        ImageIO.write(escaledImage,extension,outputStream);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        Post post3 = Post.builder()
                .titulo(createPostDto.getTitulo())
                .texto(createPostDto.getTexto())
                .postEnum(createPostDto.getPostEnum())
                .imagen(uri)
                .user(user)
                .build();

        userEntityRepository.save(user);

        return postRepository.save(post3);
    }

    public void deletePost(Long id) throws SingleEntityNotFoundException {
        Optional<Post> data = postRepository.findById(id);
        String name = StringUtils.cleanPath(String.valueOf(data.get().getImagen()))
                .replace("http://localhost:8080/download", "");
        Path path = storageService.load(name);
        String filename = StringUtils.cleanPath(String.valueOf(path))
                .replace("http://localhost:8080/download", "");

        storageService.deleteFile(filename);
        postRepository.deleteById(id);
    }

    public Optional<GetPostDto> updatePost (Long id, CreatePostDto p, MultipartFile file ,UserEntity user) throws EntityNotFoundException {

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

    public List<GetPostDto> findByPostEnum(PostEnum postEnum) {

        List<Post> listaa = postRepository.findByPostEnum(postEnum);

       return listaa.stream().map(postDtoConverter::postToGetPostDto).toList();
    }

    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }


    public List<Post> findPostByUserNickname(String nick) throws EntityNotFoundException{

        if(userEntityRepository.findAllByNick(nick).isEmpty()){
            return Collections.EMPTY_LIST;
        }else{
            return userEntityRepository.findAllByNick(nick).get().getPosts();
        }
    }

    }


