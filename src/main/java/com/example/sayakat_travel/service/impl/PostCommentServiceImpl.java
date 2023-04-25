package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.helpers.GetUserAndPost;
import com.example.sayakat_travel.repositories.PostCommentRepository;
import com.example.sayakat_travel.repositories.PostRepository;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostCommentServiceImpl implements PostCommentService {


    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;

    @Override
    public ArrayList<PostComment> getAllCommentsByPost(UUID postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null || post.getStatus() != PostPlaceEventStatus.CONFIRMED){
            throw new ApiRequestException("Not Found Post: "+postId);
        }
        return postCommentRepository.findAllByPostAndCommentStatus(post,CommentStatus.ACTIVE).orElse(null);
    }

    @Override
    public PostComment saveNewCommentToPost(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID postId) {
        GetUserAndPost getUserAndPost = new GetUserAndPost(postId, postAndPlaceCommentCreateDto.getUserId(),postRepository,userRepository);
        PostComment postComment = PostComment.builder()
                .post(getUserAndPost.findPost())
                .user(getUserAndPost.findUser())
                .comment(postAndPlaceCommentCreateDto.getComment())
                .commentStatus(CommentStatus.ACTIVE)
                .build();
        return postCommentRepository.save(postComment);
    }

    @Override
    public PostComment updateStatus(Long postCommentId, CommentStatus status) {
        PostComment postComment = postCommentRepository.findById(postCommentId).orElse(null);
        if(postComment == null){
            throw new ApiRequestException("Not Found Post Comment ID:"+postCommentId);
        }
        postComment.setCommentStatus(status);
        return postComment;
    }
}
