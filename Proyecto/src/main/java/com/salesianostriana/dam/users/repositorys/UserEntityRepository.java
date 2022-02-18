package com.salesianostriana.dam.users.repositorys;

import com.salesianostriana.dam.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);
    @Override
    Optional<UserEntity> findById(UUID uuid);

    Optional<UserEntity> findAllByNick (String nick);

    UserEntity findByNick(String nick);

    UserEntity findByFollowingContains(UserEntity user);

}

