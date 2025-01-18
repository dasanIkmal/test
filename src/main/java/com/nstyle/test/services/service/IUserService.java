package com.nstyle.test.services.service;

import com.nstyle.test.dtos.UserDTO;
import com.nstyle.test.entities.UserEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> getAllUsers();

    UserEntity getSingleUser(Long id);

    Void deleteUser(Long id);

    Void updateUser(Long id, UserDTO userDTO);
}
