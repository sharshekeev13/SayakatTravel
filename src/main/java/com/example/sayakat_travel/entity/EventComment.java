package com.example.sayakat_travel.entity;

import com.example.sayakat_travel.entity.enums.CommentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "event_comment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventComment {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User user;

    @ManyToOne
    Event event;
    String comment;
    CommentStatus commentStatus;

}
