package com.example.sayakat_travel.dto.request;

import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostAndPlaceUpdateDto {
    String title;
    String description;
    MultipartFile photo;
    PostPlaceEventStatus status;
}
