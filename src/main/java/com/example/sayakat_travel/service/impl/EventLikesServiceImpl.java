package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.entity.*;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.*;
import com.example.sayakat_travel.service.EventLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EventLikesServiceImpl implements EventLikesService {

    private final EventLikesRepository eventLikesRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    @Override
    public EventLikes saveLikeToEvent(UUID eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(event == null || event.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Place: "+eventId);
        }
        if (user == null){
            throw new ApiRequestException("Not Found User: "+userId);
        }
        EventLikes eventLikes = EventLikes.builder()
                .event(event)
                .user(user)
                .build();
        return eventLikesRepository.save(eventLikes);
    }

    @Override
    public void deleteLikeFromEvent(UUID eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(event == null || event.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Place: "+eventId);
        }
        if (user == null){
            throw new ApiRequestException("Not Found User: "+userId);
        }
        EventLikes eventLikes = eventLikesRepository.findByEventAndUser(event,user).orElse(null);
        if(eventLikes == null){
            throw new ApiRequestException("Not Found Like To Place: "+eventId);
        }
        eventLikesRepository.delete(eventLikes);
    }
}
