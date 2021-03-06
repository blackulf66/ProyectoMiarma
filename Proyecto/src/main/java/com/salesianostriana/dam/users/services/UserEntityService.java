package com.salesianostriana.dam.users.services;

import com.salesianostriana.dam.dto.peticion.CreateSeguimientoDTO;
import com.salesianostriana.dam.dto.post.CreatePostDto;
import com.salesianostriana.dam.dto.post.GetPostDto;
import com.salesianostriana.dam.exception.UserException;
import com.salesianostriana.dam.model.Post;
import com.salesianostriana.dam.model.Seguimiento;
import com.salesianostriana.dam.repository.SeguimientoRepository;
import com.salesianostriana.dam.service.BaseService;
import com.salesianostriana.dam.service.StorageService;
import com.salesianostriana.dam.users.dtos.CreateUserDto;
import com.salesianostriana.dam.users.dtos.GetUserDto;
import com.salesianostriana.dam.users.dtos.UserDtoConverter;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.models.UserRole;
import com.salesianostriana.dam.users.repositorys.UserEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;
    private final UserEntityRepository userEntityRepository;
    private final UserDtoConverter userDtoConverter;
    private final SeguimientoRepository seguimientoRepository;

        public UserEntity saveuser(CreateUserDto newUser, MultipartFile file) throws IOException {

            String filename = storageService.store(file);

            String extension = StringUtils.getFilenameExtension(filename);

            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            BufferedImage scaledImage = storageService.simpleResizer(originalImage,128);

            OutputStream outputStream = Files.newOutputStream(storageService.load(filename));

            ImageIO.write(scaledImage,extension,outputStream);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(filename)
                    .toUriString();

            if (newUser.getPassword().contentEquals(newUser.getPassword())) {
                UserEntity userEntity = UserEntity.builder()
                        .password(passwordEncoder.encode(newUser.getPassword()))
                        .avatar(uri)
                        .nick(newUser.getNick())
                        .email(newUser.getEmail())
                        .role(UserRole.USER)
                        .perfilprivado(newUser.isPerfilPrivado())
                        .posts(newUser.getPosts())
                        .build();
                return save(userEntity);
            } else {
                return null;
            }
        }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repositorio.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " no encontrado"));
    }

    public Optional<UserEntity> finduserById(UUID id){
        return userEntityRepository.findById(id);
    }

    public Optional<GetUserDto> updateUser (UUID id, CreateUserDto p, MultipartFile file , UserEntity user) throws EntityNotFoundException {

        Optional<UserEntity> data = userEntityRepository.findById(id);
        String name = StringUtils.cleanPath(String.valueOf(data.get().getAvatar())).replace("http://localhost:8080/download", "");
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
            m.setNick(p.getNick());
            m.setAvatar(uri);
            m.setEmail(p.getEmail());
            m.setPassword(p.getPassword());
            userEntityRepository.save(m);
            return userDtoConverter.convertUserEntityToGetUserDto(m);
        });
    }

    public Optional<GetUserDto> actualizarPerfil(UserEntity user, CreateUserDto u, MultipartFile file) throws Exception {
        if (file.isEmpty()){
            Optional<UserEntity> data = userEntityRepository.findById(user.getId());
            return data.map(m -> {
                m.setNick(u.getNick());
                m.setEmail(u.getEmail());
                m.setAvatar(u.getAvatar());
                m.setAvatar(m.getAvatar());
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.convertUserEntityToGetUserDto(m);
            });
        }else{

            Optional<UserEntity> data = userEntityRepository.findById(user.getId());
            String name = StringUtils.cleanPath(String.valueOf(data.get().getAvatar())).replace("http://localhost:8080/download/", "");
            Path p = storageService.load(name);
            String filename = StringUtils.cleanPath(String.valueOf(p)).replace("http://localhost:8080/download/", "");
            Path pa = Paths.get(filename);
            storageService.deleteFile(pa.toString());
            String avatar = storageService.storePost(file);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(avatar)
                    .toUriString();

            return data.map(m -> {
                m.setNick(u.getNick());
                m.setAvatar(u.getAvatar());
                m.setAvatar(uri);
                m.setEmail(u.getEmail());
                userEntityRepository.save(m);
                return userDtoConverter.convertUserEntityToGetUserDto(m);
            });

        }
    }

    public Seguimiento sendSeguimiento (String nick, CreateSeguimientoDTO createSeguimientoDto, UserEntity user){
        UserEntity usuario= userEntityRepository.findByNick(nick);
        if(usuario==null || usuario.getNick().equals(user.getNick())){
            throw new UserException("No puedes enviarte peticiones a ti mismo");
        }else{
            Seguimiento seguimiento = Seguimiento.builder()
                    .peticion(createSeguimientoDto.getTexto() + user.getNick())
                    .origen(user)
                    .destino(usuario)
                    .build();
            usuario.addPeticion(seguimiento);
            userEntityRepository.save(usuario);
            seguimientoRepository.save(seguimiento);
            return seguimiento;
        }
    }

    public void aceptarSeguimiento(Long id, UserEntity user){

        Optional<Seguimiento> peticion = seguimientoRepository.findById(id);
        user.addSeguidor(peticion.get().getOrigen());
        save(user);
        peticion.get().nullearDestinatarios();
        seguimientoRepository.save(peticion.get());
        seguimientoRepository.deleteById(id);
    }

    public void rechazarSeguimiento(Long id){
        Optional<Seguimiento> peticionSeguimiento = seguimientoRepository.findById(id);
        peticionSeguimiento.get().nullearDestinatarios();
        seguimientoRepository.save(peticionSeguimiento.get());
        seguimientoRepository.deleteById(id);
    }





}