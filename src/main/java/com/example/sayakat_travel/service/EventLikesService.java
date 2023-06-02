package com.example.sayakat_travel.service;

import com.example.sayakat_travel.entity.EventLikes;
import com.example.sayakat_travel.entity.PlaceLikes;

import java.util.UUID;

public interface EventLikesService {


    EventLikes saveLikeToEvent(UUID eventId, Long userId);

    void deleteLikeFromEvent(UUID eventId,Long userId);

}
