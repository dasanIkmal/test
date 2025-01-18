package com.nstyle.test.services.service;

import com.nstyle.test.dtos.OTPDTO;
import com.nstyle.test.dtos.UserDTO;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.exceptions.OTPValidatationException;
import com.nstyle.test.exceptions.UserAlreadyExistException;
import com.nstyle.test.response.ServiceResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;

public interface IAuthService extends UserDetailsService {
    String register(UserDTO user) throws UserAlreadyExistException;

    ServiceResponse<Void> validateOTP(OTPDTO otpDTO) throws OTPValidatationException;

    UserEntity findUserByUserName(String userName);
}
