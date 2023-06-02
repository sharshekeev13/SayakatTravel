package com.example.sayakat_travel.service.impl;


import com.example.sayakat_travel.dto.request.EventCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.EventFindOneDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Event;
import com.example.sayakat_travel.entity.Place;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.*;
import com.example.sayakat_travel.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventLikesRepository eventLikesRepository;
    private final FileUploadServiceImpl fileUploadService;

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event createEvent(EventCreateDto eventCreateDto) {
        String photoUrl;
        try {
            photoUrl = fileUploadService.uploadFile(eventCreateDto.getPhoto());
        } catch (IOException e) {
            throw new ApiRequestException("Can Not Upload a Image");
        }
        Event event = Event.builder()
                .title(eventCreateDto.getTitle())
                .description(eventCreateDto.getDescription())
                .status(PostPlaceEventStatus.AWAITS)
                .photo(photoUrl)
                .createdDate(LocalDate.now())
                .build();
        return eventRepository.save(event);
    }

    @Override
    public EventFindOneDto findEventById(UUID id) {
        Event event = eventRepository.findById(id).orElse(null);
        if(event == null){
            throw new ApiRequestException("Place Not Found");
        }
        int likes = Objects.requireNonNull(eventLikesRepository.findByEventId(Objects.requireNonNull(event).getId()).orElse(null)).size();
        return EventFindOneDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .status(event.getStatus())
                .photo(event.getPhoto())
                .createdDate(event.getCreatedDate())
                .countLike(likes)
                .build();
     }

    @Override
    public Event updateEvent(UUID id, PostAndPlaceUpdateDto event) {
        Event oldEvent = eventRepository.findById(id).orElse(null);
        if(oldEvent == null){
            throw new ApiRequestException("Not Found Place:" + id);
        }else{
            if(event.getTitle() != null){
                oldEvent.setTitle(event.getTitle());
            }
            if(event.getDescription() != null){
                oldEvent.setDescription(event.getDescription());
            }
            if(event.getPhoto() != null){
                String photoUrl;
                try {
                    photoUrl = fileUploadService.uploadFile(event.getPhoto());
                } catch (IOException e) {
                    throw new ApiRequestException("Can Not Upload a Image");
                }
            }
            if(event.getStatus() != null){
                oldEvent.setStatus(event.getStatus());
            }
        }
        return eventRepository.save(oldEvent);
    }
}
