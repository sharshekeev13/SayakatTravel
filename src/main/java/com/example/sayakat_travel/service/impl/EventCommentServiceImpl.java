package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.*;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.helpers.GetUserAndPost;
import com.example.sayakat_travel.repositories.*;
import com.example.sayakat_travel.service.EventCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EventCommentServiceImpl implements EventCommentService {

    private final EventRepository eventRepository;
    private final EventCommentRepository eventCommentRepository;
    private final UserRepository userRepository;


    @Override
    public ArrayList<EventComment> getAllCommentsByEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if(event == null || event.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Post: "+eventId);
        }
        return eventCommentRepository.findAllByEventAndCommentStatus(event,CommentStatus.ACTIVE).orElse(null);    }

    @Override
    public EventComment saveNewCommentToEvent(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User user = userRepository.findById(postAndPlaceCommentCreateDto.getUserId()).orElse(null);
        if(event == null || event.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Post: "+eventId);
        }
        if(user == null){
            throw new ApiRequestException("Not Found User");
        }
        EventComment eventComment = EventComment.builder()
                .event(event)
                .user(user)
                .comment(postAndPlaceCommentCreateDto.getComment())
                .commentStatus(CommentStatus.ACTIVE)
                .build();
        return eventCommentRepository.save(eventComment);
    }

    @Override
    public EventComment updateStatus(Long eventCommentId, CommentStatus status) {
        EventComment postComment = eventCommentRepository.findById(eventCommentId).orElse(null);
        if(postComment == null){
            throw new ApiRequestException("Not Found Post Comment ID:"+eventCommentId);
        }
        postComment.setCommentStatus(status);
        return postComment;
    }
}
