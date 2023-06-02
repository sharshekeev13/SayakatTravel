package com.example.sayakat_travel.service;


import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.dto.response.AuthenticationResponse;
import com.example.sayakat_travel.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public void signUpUser(SignUpDto signUpDto,String siteUrl);

    public User getUserById(Long id);

}
