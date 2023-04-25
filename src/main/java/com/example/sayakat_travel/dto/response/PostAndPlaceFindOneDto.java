package com.example.sayakat_travel.dto.response;

import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PostAndPlaceFindOneDto {
    UUID id;
    String title;
    String description;
    String photo;
    User user;
    LocalDate createdDate;
    PostPlaceEventStatus status;
    int countLike;
}
