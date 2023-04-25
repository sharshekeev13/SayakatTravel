package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.PlaceComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;

import java.util.ArrayList;
import java.util.UUID;

public interface PlaceCommentService {

    ArrayList<PlaceComment> getAllCommentsByPlace(UUID placeId);
    PlaceComment saveNewCommentToPlace(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID placeId);
    PlaceComment updateStatus(Long placeCommentId, CommentStatus status);

}
