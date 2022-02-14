package com.salesianostriana.dam.users.services;


import com.salesianostriana.dam.service.BaseService;
import com.salesianostriana.dam.users.dtos.CreateUserDto;
import com.salesianostriana.dam.users.models.UserEntity;
import com.salesianostriana.dam.users.models.UserRole;
import com.salesianostriana.dam.users.repositorys.UserEntityRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

        public UserEntity saveuser(CreateUserDto newUser) {
            if (newUser.getPassword().contentEquals(newUser.getPassword())) {
                UserEntity userEntity = UserEntity.builder()
                        .password(passwordEncoder.encode(newUser.getPassword()))
                        .avatar(newUser.getAvatar())
                        .nick(newUser.getNick())
                        .email(newUser.getEmail())
                        .role(UserRole.USER)
                        .perfilprivado(newUser.isPerfilPrivado())
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

    public List<UserEntity> loadUserRol(UserRole userRole) throws UsernameNotFoundException {
        return this.repositorio.findUserByRole(userRole)
                .orElseThrow(()-> new UsernameNotFoundException(userRole + " no encontrado"));
    }
}