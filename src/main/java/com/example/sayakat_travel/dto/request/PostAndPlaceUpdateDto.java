package com.example.sayakat_travel.dto.request;

import com.example.sayakat_travel.entity.enums.PostStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostUpdateDto {
    String title;
    String description;
    String photo;
    PostStatus status;
}
