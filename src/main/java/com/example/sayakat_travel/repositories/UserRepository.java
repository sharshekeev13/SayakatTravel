package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(@NonNull Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String code);

}