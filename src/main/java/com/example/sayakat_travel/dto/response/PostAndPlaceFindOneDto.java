package com.example.sayakat_travel.dto.response;

import com.example.sayakat_travel.entity.PostLikes;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PostFindOneDto {
    UUID id;
    String title;
    String description;
    String photo;
    User user;
    LocalDate createdDate;
    PostStatus status;
    int countLike;
}
