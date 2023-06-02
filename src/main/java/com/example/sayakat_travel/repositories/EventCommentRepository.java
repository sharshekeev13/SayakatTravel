package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.Event;
import com.example.sayakat_travel.entity.EventComment;
import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.PlaceComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface EventCommentRepository extends JpaRepository<EventComment,Long> {
    Optional<ArrayList<EventComment>> findAllByEventAndCommentStatus(Event event, CommentStatus commentStatus);

}
