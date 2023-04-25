package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.PlaceLikes;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.*;
import com.example.sayakat_travel.service.PlaceLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PlaceLikesServiceImpl implements PlaceLikesService {

    private final PlaceLikesRepository placeLikesRepository;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;


    @Override
    public PlaceLikes saveLikeToPost(UUID placeId, Long userId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(place == null || place.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Place: "+placeId);
        }
        if (user == null){
            throw new ApiRequestException("Not Found User: "+userId);
        }
        PlaceLikes postLikes = PlaceLikes.builder()
                .place(place)
                .user(user)
                .build();
        return placeLikesRepository.save(postLikes);
    }

    @Override
    public void deleteLikeFromPost(UUID placeId, Long userId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(place == null || place.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Place: "+placeId);
        }
        if (user == null){
            throw new ApiRequestException("Not Found User: "+userId);
        }
        PlaceLikes placeLikes = placeLikesRepository.findByPlaceAndUser(place,user).orElse(null);
        if(placeLikes == null){
            throw new ApiRequestException("Not Found Like To Place: "+placeId);
        }
        placeLikesRepository.delete(placeLikes);
    }
}
