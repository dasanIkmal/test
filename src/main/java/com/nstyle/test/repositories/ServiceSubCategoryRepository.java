package com.nstyle.test.repositories;

import com.nstyle.test.entities.ServiceSubCategoryEntity;
import com.nstyle.test.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ServiceSubCategoryRepository extends JpaRepository<ServiceSubCategoryEntity,Long> {

}
