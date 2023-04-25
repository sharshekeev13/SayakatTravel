package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.entity.PostLikes;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.helpers.GetUserAndPost;
import com.example.sayakat_travel.repositories.PostLikesRepository;
import com.example.sayakat_travel.repositories.PostRepository;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostLikesServiceImpl implements PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostLikes saveLikeToPost(UUID postId, Long userId) {
        GetUserAndPost getUserAndPost = new GetUserAndPost(postId,userId,postRepository,userRepository);
        PostLikes postLikes = PostLikes.builder()
                .post(getUserAndPost.findPost())
                .user(getUserAndPost.findUser())
                .build();
        return postLikesRepository.save(postLikes);
    }

    @Override
    public void deleteLikeFromPost(UUID postId,Long userId) {
        GetUserAndPost getUserAndPost = new GetUserAndPost(postId,userId,postRepository,userRepository);
        PostLikes postLike = postLikesRepository.findByPostAndUser(getUserAndPost.findPost(), getUserAndPost.findUser()).orElse(null);
        if(postLike == null){
            throw new ApiRequestException("Post Like not found");
        }
        postLikesRepository.delete(postLike);
    }

}
