package com.example.sayakat_travel.entity;


import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "posts")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    UUID id;

    String title;
    String description;

    String photo;

    @ManyToOne
    User user;

    LocalDate createdDate;

    PostPlaceEventStatus status;
}
