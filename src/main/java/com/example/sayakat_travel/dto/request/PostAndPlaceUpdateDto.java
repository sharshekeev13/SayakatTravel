package com.example.sayakat_travel.dto.request;

import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import lombok.Data;

@Data
public class PostAndPlaceUpdateDto {
    String title;
    String description;
    String photo;
    PostPlaceEventStatus status;
}
