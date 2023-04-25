package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface PlaceService {

    public List<Place> getAll();
    public Place createPlace(PostAndPlaceCreateDto postAndPlaceCreateDto);

    public PostAndPlaceFindOneDto findPlaceById(UUID id);

    public Place updatePlace(UUID id, PostAndPlaceUpdateDto place);

}
