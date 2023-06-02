package com.example.sayakat_travel.entity;


import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "event")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String title;
    String description;
    @Column(name = "created_date")
    LocalDate createdDate;
    LocalDate time;
    String photo;
    PostPlaceEventStatus status;
}
