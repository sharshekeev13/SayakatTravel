package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.Roles;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Override
    public User signUpUser(SignUpDto signUpDto) {
        User user =  User.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .photo(signUpDto.getPhoto())
                .firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .middleName(signUpDto.getMiddleName())
                .role(Roles.USER)
                .phoneNumber(signUpDto.getPhoneNumber())
                .userInfo(signUpDto.getUserInfo())
                .birthday(LocalDate.now())
                .createdDate(LocalDate.now())
                .build();
        return repository.save(user);
    }
}
