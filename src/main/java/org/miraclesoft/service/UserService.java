package org.miraclesoft.service;
import org.miraclesoft.domain.Order;
import org.miraclesoft.domain.jwt.UserAuth;
import org.miraclesoft.repository.OrderRepository;
import org.miraclesoft.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserAuthRepository userAuthRepository;

    @Autowired
    public UserService(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    public List<UserAuth> getAllUsers() {
        return userAuthRepository.findAll();
    }

    public UserAuth getUserByEmail(String email) {
        return userAuthRepository.findByEmail(email).orElse(null);
    }

    public String deleteUser(String email) {
        if (userAuthRepository.existsById(email)) {
            userAuthRepository.deleteById(email);
            return email;
        } else {
            return null;
        }
    }
}
