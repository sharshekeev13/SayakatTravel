package com.example.sayakat_travel.service;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;

import java.util.ArrayList;
import java.util.UUID;

public interface PostCommentService {
    ArrayList<PostComment> getAllCommentsByPost(UUID postId);
    PostComment saveNewCommentToPost(PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto, UUID postId);
    PostComment updateStatus(Long postCommentId,CommentStatus status);
}
