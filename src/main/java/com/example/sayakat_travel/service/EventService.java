package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.EventCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.EventFindOneDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Event;
import com.example.sayakat_travel.entity.Place;

import java.util.List;
import java.util.UUID;

public interface EventService {


    public List<Event> getAll();
    public Event createEvent(EventCreateDto eventCreateDto);

    public EventFindOneDto findEventById(UUID id);

    public Event updateEvent(UUID id, PostAndPlaceUpdateDto event);

}
