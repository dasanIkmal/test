package com.nstyle.test.services;

import com.nstyle.test.dtos.UserDTO;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.repositories.UserRepository;
import com.nstyle.test.services.service.IUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getSingleUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserDAO not found with id: " + id));
    }

    @Override
    public Void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("UserDAO not found with id: " + id));
        userRepository.deleteById(user.getUserId());
        return null;
    }

    @Override
    public Void updateUser(Long id, UserDTO userDTO) {
        return null;
    }
}
