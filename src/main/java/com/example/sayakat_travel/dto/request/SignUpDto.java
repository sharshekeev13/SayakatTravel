package com.example.sayakat_travel.dto.request;

import lombok.Data;

import javax.management.relation.Role;
import java.time.LocalDate;

@Data
public class SighUpDto {

    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    LocalDate birthday;
    String userInfo;
    String phoneNumber;
    Role role;


}
