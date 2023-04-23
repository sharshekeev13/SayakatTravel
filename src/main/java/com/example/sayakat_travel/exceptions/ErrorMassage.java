package com.example.sayakat_travel.helpers;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMassage extends RuntimeException{
    private int status;
    private String message;

}
