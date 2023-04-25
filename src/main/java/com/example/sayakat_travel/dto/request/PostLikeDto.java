package com.example.sayakat_travel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class PostLikeDto {

    @NotEmpty(message = "PostID is blank")
    UUID postId;
    @NotEmpty(message = "UserID is blank")
    Long userId;
}
