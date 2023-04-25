package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostLikeDto;
import com.example.sayakat_travel.entity.PostLikes;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface PostLikesService {

    PostLikes saveLikeToPost(UUID postId, Long userId);

    void deleteLikeFromPost(UUID postId,Long userId);

}
