package com.example.sayakat_travel.entity;

import com.example.sayakat_travel.entity.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Email
    @Column(unique = true)
    String email;

    @JsonIgnore
    String password;

    String photo;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "middle_name")
    String middleName;

    LocalDate birthday;

    Roles role;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "user_info")
    String userInfo;

    @Column(name = "created_date")
    LocalDate createdDate;

}
