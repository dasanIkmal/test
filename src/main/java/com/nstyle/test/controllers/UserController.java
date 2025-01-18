package com.nstyle.test.controllers;

import com.nstyle.test.dtos.UserDTO;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.response.ServiceResponse;
import com.nstyle.test.services.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ServiceResponse<List<UserEntity>> getAllUsers(){
        return new ServiceResponse<>(HttpStatus.OK.value(), "ALl users",userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ServiceResponse<UserEntity> getSingleUser(@PathVariable("id") Long  id){
        return new ServiceResponse<>(HttpStatus.OK.value(),"Single User",userService.getSingleUser(id));
    }

    @PutMapping("/{id}")
    public ServiceResponse<Void> updateUser(@PathVariable("id") Long  id, @RequestBody UserDTO userDTO){
        return new ServiceResponse<>(HttpStatus.OK.value(),"User updated",userService.updateUser(id,userDTO));
    }

    @DeleteMapping("/{id}")
    public ServiceResponse<Void> deleteUser(@PathVariable("id") Long  id){
        return new ServiceResponse<>(HttpStatus.OK.value(),"User deleted",userService.deleteUser(id));
    }

}
