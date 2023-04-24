package com.example.sayakat_travel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostCommentCreateDto {
    @NotEmpty(message = "UserID is blank")
    Long userId;
    @NotEmpty(message = "Comment is blank")
    String comment;
}
