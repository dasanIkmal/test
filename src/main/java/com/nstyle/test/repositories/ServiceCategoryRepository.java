package com.nstyle.test.repositories;

import com.nstyle.test.entities.ServiceCategoryEntity;
import com.nstyle.test.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategoryEntity,Long> {

}
