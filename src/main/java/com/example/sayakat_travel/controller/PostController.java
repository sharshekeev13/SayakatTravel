package com.example.sayakat_travel.controller;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.PostLikes;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.service.PostCommentService;
import com.example.sayakat_travel.service.impl.PostLikesServiceImpl;
import com.example.sayakat_travel.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;
    private final PostLikesServiceImpl postLikesService;
    private final PostCommentService postCommentService;

    @RequestMapping(path = "",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Post> createPost(@ModelAttribute PostAndPlaceCreateDto postAndPlaceCreateDto){
        return ResponseEntity.ok(postService.createPost(postAndPlaceCreateDto));
    }

    @RequestMapping(path = "/{id}",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Post> updatePost(@PathVariable UUID id,@ModelAttribute PostAndPlaceUpdateDto post){
        return ResponseEntity.ok(postService.updatePost(id,post));
    }


    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<PostLikes> saveLikeToPost(@PathVariable UUID postId,@PathVariable Long userId){
        return ResponseEntity.ok(postLikesService.saveLikeToPost(postId,userId));
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<String> deleteLike(@PathVariable UUID postId,@PathVariable Long userId){
        postLikesService.deleteLikeFromPost(postId,userId);
        return ResponseEntity.ok("Deleted like from post " + postId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostAndPlaceFindOneDto> findPostById(@PathVariable UUID id){
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<ArrayList<PostComment>> getAllCommentsByPost(@PathVariable UUID id){
        return ResponseEntity.ok(postCommentService.getAllCommentsByPost(id));
    }

    @PostMapping("{id}/comment/create")
    public ResponseEntity<PostComment> createCommentToPost(@PathVariable UUID id,@RequestBody PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto){
        return ResponseEntity.ok(postCommentService.saveNewCommentToPost(postAndPlaceCommentCreateDto,id));
    }

    @PostMapping("/{id}/comment/{status}")
    public ResponseEntity<PostComment> updateCommentStatus(@PathVariable Long id, @PathVariable CommentStatus status){
        return ResponseEntity.ok(postCommentService.updateStatus(id,status));
    }
}
