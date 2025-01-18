package com.nstyle.test.services;

import com.nstyle.test.dtos.OTPDTO;
import com.nstyle.test.dtos.UserDTO;

import com.nstyle.test.entities.OTPEntity;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.exceptions.OTPValidatationException;
import com.nstyle.test.exceptions.UserAlreadyExistException;
import com.nstyle.test.repositories.OTPRepository;
import com.nstyle.test.repositories.UserRepository;
import com.nstyle.test.response.ServiceResponse;
import com.nstyle.test.services.service.IAuthService;
import com.nstyle.test.services.service.IEmailService;
import com.nstyle.test.utils.OTPGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImp implements IAuthService {


    private final UserRepository userRepository;

    private final IEmailService IEmailService;

    private final OTPRepository otpRepository;

    public AuthServiceImp(UserRepository userRepository, IEmailService IEmailService, OTPRepository otpRepository) {
        this.userRepository = userRepository;
        this.IEmailService = IEmailService;
        this.otpRepository = otpRepository;
    }

    @Override
    public String register(UserDTO userDTO) throws UserAlreadyExistException {
        if (userRepository.findByUserNameOrUserEmail(userDTO.getName(),userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User with username or email");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

        UserEntity userEntity = UserEntity.builder()
                .userName(userDTO.getName())
                .userEmail(userDTO.getEmail())
                .role("user")
                .userPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()))
                .build();

        userRepository.saveAndFlush(userEntity);

        int generateOTP = OTPGenerator.generateOTP();

        OTPEntity otpEntity = OTPEntity.builder()
                .userID(userEntity.getUserId())
                .otp(generateOTP)
                .build();

        otpRepository.save(otpEntity);
        IEmailService.sendEmail(userEntity.getUserEmail(),"Please Complete the Registration", IEmailService.generateEmailBody(userEntity.getUserName(),generateOTP));
        return "Email sent!!";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = Optional.ofNullable(userRepository.findByUserName(username)).orElseThrow(() -> new UsernameNotFoundException("UserDAO not found with username: " + username));
        return User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getUserPassword())
                .roles(userEntity.getRole())
                .build();
    }

    @Override
    public ServiceResponse<Void> validateOTP(OTPDTO otpdto) throws OTPValidatationException {
        UserEntity user =userRepository.findByUserEmail(otpdto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("UserDAO not found " ));
        OTPEntity otp = otpRepository.findByUserID(user.getUserId());
        if (!otp.getOtp().equals(otpdto.getOtp())){
            throw new OTPValidatationException("OTP is invalid");
        }
        return new ServiceResponse<>(HttpStatus.ACCEPTED.value(),"OTP validation Success");
    }

    @Override
    public UserEntity findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }


}
