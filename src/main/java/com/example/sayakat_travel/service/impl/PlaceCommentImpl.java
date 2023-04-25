package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.PlaceComment;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.*;
import com.example.sayakat_travel.service.PlaceCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PlaceCommentImpl implements PlaceCommentService {

    private final PlaceRepository placeRepository;
    private final PlaceCommentRepository placeCommentRepository;
    private final UserRepository userRepository;


    @Override
    public ArrayList<PlaceComment> getAllCommentsByPlace(UUID placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if(place == null || place.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Place: "+placeId);
        }
        return placeCommentRepository.findAllByPlaceAndCommentStatus(place,CommentStatus.ACTIVE).orElse(null);
    }

    @Override
    public PlaceComment saveNewCommentToPlace(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if(place == null || place.getStatus() == PostPlaceEventStatus.AWAITS){
            throw new ApiRequestException("Not Found Place: "+placeId);
        }
        User user = userRepository.findById(postAndPlaceCommentCreateDto.getUserId()).orElse(null);
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }

        PlaceComment placeComment = PlaceComment.builder()
                .place(place)
                .user(user)
                .comment(postAndPlaceCommentCreateDto.getComment())
                .commentStatus(CommentStatus.ACTIVE)
                .build();
        return placeCommentRepository.save(placeComment);
    }

    @Override
    public PlaceComment updateStatus(Long placeCommentId, CommentStatus status) {
        PlaceComment placeComment = placeCommentRepository.findById(placeCommentId).orElse(null);
        if(placeComment == null){
            throw new ApiRequestException("Not Found Post Comment ID:" + placeCommentId);
        }
        placeComment.setCommentStatus(status);
        return placeComment;
    }
}
