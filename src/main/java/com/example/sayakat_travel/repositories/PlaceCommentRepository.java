package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.PlaceComment;
import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PlaceCommentRepository extends JpaRepository<PlaceComment,Long> {
    Optional<ArrayList<PlaceComment>> findAllByPlaceAndCommentStatus(Place place, CommentStatus commentStatus);

}
