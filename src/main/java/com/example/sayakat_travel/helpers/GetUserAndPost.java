package com.example.sayakat_travel.helpers;

import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.PostRepository;
import com.example.sayakat_travel.repositories.UserRepository;
import lombok.Data;

import java.util.UUID;

@Data
public class GetUserAndPost {
    private UUID postId;
    private Long userId;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public GetUserAndPost(UUID postId, Long userId, PostRepository postRepository, UserRepository userRepository){
        this.postId = postId;
        this.userId = userId;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post findPost(){
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null || post.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Post: "+postId);
        }
        return post;
    }

    public User findUser(){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new ApiRequestException("Not Found User: " + userId);
        }
        return user;
    }
}
