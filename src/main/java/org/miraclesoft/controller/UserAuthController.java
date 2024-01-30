package org.miraclesoft.controller;


import org.miraclesoft.domain.jwt.UserAuth;
import org.miraclesoft.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/use")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;


    @GetMapping("/test-users")
    public List<UserAuth> getUserAuths(){
        return userAuthService.getUserAuths();
    }
    @GetMapping("/hello")
    public String getHello(){
        return "HELLO WORLD";
    }
}
