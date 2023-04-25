package com.example.sayakat_travel.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostAndPlaceCommentCreateDto {
    @NotEmpty(message = "UserID is blank")
    Long userId;
    @NotEmpty(message = "Comment is blank")
    String comment;
}
