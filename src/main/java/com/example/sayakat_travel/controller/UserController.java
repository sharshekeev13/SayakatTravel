package com.example.sayakat_travel.controller;

import com.example.sayakat_travel.dto.request.SignInDto;
import com.example.sayakat_travel.dto.request.SignUpDto;
import com.example.sayakat_travel.dto.response.AuthenticationResponse;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.helpers.Helpers;
import com.example.sayakat_travel.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;


    @RequestMapping(path = "/sign_up", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> signUpUser(@Valid @ModelAttribute SignUpDto signUpDto,HttpServletRequest request){
        userService.signUpUser(signUpDto, Helpers.getSiteURL(request));
        return ResponseEntity.ok("Verification code is send");
    }

    @PostMapping("/sign_in")
    public ResponseEntity<AuthenticationResponse> signInUser(@Valid @RequestBody SignInDto signInDto){
        return ResponseEntity.ok(userService.signInUser(signInDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }


    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

}
