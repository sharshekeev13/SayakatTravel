package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.EventComment;
import com.example.sayakat_travel.entity.PlaceComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;

import java.util.ArrayList;
import java.util.UUID;


public interface EventCommentService {


    ArrayList<EventComment> getAllCommentsByEvent(UUID eventId);
    EventComment saveNewCommentToEvent(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID eventId);
    EventComment updateStatus(Long eventCommentId, CommentStatus status);

}
