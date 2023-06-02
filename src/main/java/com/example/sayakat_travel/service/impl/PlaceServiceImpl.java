package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.PlaceLikesRepository;
import com.example.sayakat_travel.repositories.PlaceRepository;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceLikesRepository placeLikesRepository;
    private final FileUploadServiceImpl fileUploadService;


    @Override
    public List<Place> getAll() {
        return placeRepository.findAll();
    }

    @Override
    public Place createPlace(PostAndPlaceCreateDto postAndPlaceCreateDto) {
        User user = userRepository.findById(postAndPlaceCreateDto.getUserId()).orElse(null);
        String photoUrl;
        try {
            photoUrl = fileUploadService.uploadFile(postAndPlaceCreateDto.getPhoto());
        } catch (IOException e) {
            throw new ApiRequestException("Can Not Upload a Image");
        }
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }
        Place place = Place.builder()
                .title(postAndPlaceCreateDto.getTitle())
                .description(postAndPlaceCreateDto.getDescription())
                .status(PostPlaceEventStatus.AWAITS)
                .photo(photoUrl)
                .createdDate(LocalDate.now())
                .user(user)
                .build();
        return placeRepository.save(place);
    }

    @Override
    public PostAndPlaceFindOneDto findPlaceById(UUID id) {
        Place place = placeRepository.findById(id).orElse(null);
        if(place == null){
            throw new ApiRequestException("Place Not Found");
        }
        int likes = Objects.requireNonNull(placeLikesRepository.findByPlaceId(Objects.requireNonNull(place).getId()).orElse(null)).size();
        return PostAndPlaceFindOneDto.builder()
                .id(place.getId())
                .title(place.getTitle())
                .description(place.getDescription())
                .user(place.getUser())
                .status(place.getStatus())
                .photo(place.getPhoto())
                .createdDate(place.getCreatedDate())
                .countLike(likes)
                .build();
    }

    @Override
    public Place updatePlace(UUID id, PostAndPlaceUpdateDto place) {
        Place oldPlace = placeRepository.findById(id).orElse(null);
        if(oldPlace == null){
            throw new ApiRequestException("Not Found Place:" + id);
        }else{
            if(place.getTitle() != null){
                oldPlace.setTitle(place.getTitle());
            }
            if(place.getDescription() != null){
                oldPlace.setDescription(place.getDescription());
            }
            if(place.getPhoto() != null){
                String photoUrl;
                try {
                    photoUrl = fileUploadService.uploadFile(place.getPhoto());
                } catch (IOException e) {
                    throw new ApiRequestException("Can Not Upload a Image");
                }
                oldPlace.setPhoto(photoUrl);
            }
            if(place.getStatus() != null){
                oldPlace.setStatus(place.getStatus());
            }
        }
        return placeRepository.save(oldPlace);
    }
}
