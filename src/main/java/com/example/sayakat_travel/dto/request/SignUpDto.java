package com.example.sayakat_travel.dto.request;

import com.example.sayakat_travel.entity.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpDto {
    @NotEmpty(message = "Email is blank")
    @Email(message = "Email is invalid")
    String email;
    @NotEmpty(message = "Password is blank")
    @Size(min = 8,max = 16)
    String password;
    @NotEmpty(message = "First Name is blank")
    String firstName;
    @NotEmpty(message = "Last Name is blank")
    String lastName;
    @NotEmpty(message = "Middle Name is blank")
    String middleName;
    @NotEmpty(message = "birthday is blank")
    String birthday;
    String userInfo;
    @NotEmpty(message = "Phone number is blank")
    String phoneNumber;
    MultipartFile photo;
}
