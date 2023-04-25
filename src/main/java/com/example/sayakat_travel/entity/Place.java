package com.example.sayakat_travel.entity;


import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "place")
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String title;
    String description;

    @ManyToOne
    User user;
    @Column(name = "created_date")
    LocalDate createdDate;
    String photo;
    PostPlaceEventStatus status;
}
