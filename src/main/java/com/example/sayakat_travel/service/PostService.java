package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Post;

import java.util.UUID;

public interface PostService {

    public Post createPost(PostAndPlaceCreateDto postAndPlaceCreateDto);

    public PostAndPlaceFindOneDto findPostById(UUID id);

    public Post updatePost(UUID id, PostAndPlaceUpdateDto post);

}
