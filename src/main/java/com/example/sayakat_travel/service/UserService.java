package com.example.sayakat_travel.service;


import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.dto.response.AuthenticationResponse;

public interface UserService {

    public AuthenticationResponse signUpUser(SignUpDto signUpDto);

}
