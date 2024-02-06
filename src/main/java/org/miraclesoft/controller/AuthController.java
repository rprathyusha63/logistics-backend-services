package org.miraclesoft.controller;

import org.miraclesoft.domain.jwt.JwtRequest;
import org.miraclesoft.domain.jwt.JwtResponse;
import org.miraclesoft.domain.jwt.UserAuth;
import org.miraclesoft.security.JwtHelper;
import org.miraclesoft.service.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userAuthDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userAuthDetails = userAuthDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userAuthDetails);
        UserAuth user = (UserAuth)userAuthDetails;
        JwtResponse response = JwtResponse.builder()
                .jwttoken(token)
                .username(user.getFirstName()+ " " + user.getLastName())
                .email(user.getEmail())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> exceptionHandler() {
        return new ResponseEntity<>("Credentials Invalid !!",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create-user")
    public UserAuth createUserAuth(@RequestBody UserAuth userAuth){
        userAuth.setUserId(userAuth.getEmail());
        userAuth.setUsername(userAuth.getEmail());
        return userAuthService.createUserAuth(userAuth);
    }

}