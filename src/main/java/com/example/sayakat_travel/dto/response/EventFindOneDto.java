package com.example.sayakat_travel.dto.response;

import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class EventFindOneDto {
    UUID id;
    String title;
    String description;
    String photo;
    LocalDate createdDate;
    LocalDate time;
    PostPlaceEventStatus status;
    int countLike;
}
