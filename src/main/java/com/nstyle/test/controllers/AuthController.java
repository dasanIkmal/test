package com.nstyle.test.controllers;

import com.nstyle.test.dtos.AuthRequestDTO;
import com.nstyle.test.dtos.OTPDTO;
import com.nstyle.test.dtos.UserDTO;
import com.nstyle.test.entities.UserEntity;
import com.nstyle.test.exceptions.OTPValidatationException;
import com.nstyle.test.exceptions.UserAlreadyExistException;
import com.nstyle.test.response.ErrorResponse;
import com.nstyle.test.response.ServiceResponse;
import com.nstyle.test.services.service.IAuthService;
import com.nstyle.test.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService iAuthService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(IAuthService iAuthService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.iAuthService = iAuthService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO user) throws UserAlreadyExistException {
        return ResponseEntity.ok(iAuthService.register(user));
    }


    @PostMapping("/login")
    public ServiceResponse<HashMap<String,Object>> login(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = iAuthService.findUserByUserName(userDetails.getUsername());
        HashMap<String, Object> jwt =new HashMap<>();
        jwt.put("jwt",jwtUtil.generateToken(userDetails));
        jwt.put("user",userEntity);
        return new ServiceResponse<>(HttpStatus.CREATED.value(),"Authenticated" ,jwt);
    }

    @PostMapping("/validateOTP")    
    private ServiceResponse<Void> validateOTP(@RequestBody OTPDTO otpDTO) throws OTPValidatationException {
        return iAuthService.validateOTP(otpDTO);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleInvitationCodeException(UserAlreadyExistException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OTPValidatationException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(OTPValidatationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
