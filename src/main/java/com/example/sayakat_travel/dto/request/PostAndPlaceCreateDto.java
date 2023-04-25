package com.example.sayakat_travel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostAndPlaceCreateDto {
    @NotEmpty(message = "Title is blank")
    String title;
    @NotEmpty(message = "Description is blank")
    String description;
    String photo;
    @NotEmpty(message = "UserID is blank")
    Long userId;
}
