package com.example.sayakat_travel.controller;

import com.example.sayakat_travel.dto.request.SignInDto;
import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.dto.response.AuthenticationResponse;
import com.example.sayakat_travel.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/sign_up")
    public ResponseEntity<AuthenticationResponse> signUpUser(@Valid @RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(userService.signUpUser(signUpDto));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<AuthenticationResponse> signInUser(@Valid @RequestBody SignInDto signInDto){
        return ResponseEntity.ok(userService.signInUser(signInDto));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }



}
