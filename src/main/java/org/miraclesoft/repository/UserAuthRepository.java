package org.miraclesoft.repository;

import org.miraclesoft.domain.jwt.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, String> {
    public Optional<UserAuth> findByEmail(String email);
}