package com.example.sayakat_travel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostAndPlaceCreateDto {
    @NotEmpty(message = "Title is blank")
    String title;
    @NotEmpty(message = "Description is blank")
    String description;
    MultipartFile photo;
    @NotEmpty(message = "UserID is blank")
    Long userId;
}
