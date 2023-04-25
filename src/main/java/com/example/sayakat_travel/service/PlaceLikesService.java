package com.example.sayakat_travel.service;

import com.example.sayakat_travel.entity.PlaceLikes;
import com.example.sayakat_travel.entity.PostLikes;

import java.util.UUID;

public interface PlaceLikesService {

    PlaceLikes saveLikeToPost(UUID placeId, Long userId);

    void deleteLikeFromPost(UUID placeId,Long userId);


}
