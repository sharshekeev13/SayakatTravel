package com.example.sayakat_travel.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotEmpty(message = "email is blank")
    @Email(message = "email is incorrect")
    private String email;
    @NotEmpty(message = "password is blank")
    private String password;

}
