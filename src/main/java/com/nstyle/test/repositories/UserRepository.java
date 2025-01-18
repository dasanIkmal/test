package com.nstyle.test.repositories;

import com.nstyle.test.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUserEmail(String email);

    UserEntity findByUserName(String username);

    Optional<Object> findByUserNameOrUserEmail(String name, String email);
}
