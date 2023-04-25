package com.example.sayakat_travel.service;


import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.entity.User;

public interface UserService {

    public User signUpUser(SignUpDto signUpDto);

}
