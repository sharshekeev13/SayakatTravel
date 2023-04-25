package com.example.sayakat_travel.controller;

import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUpUser(@Valid @RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok(userService.signUpUser(signUpDto));
    }

}
