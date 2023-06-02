package com.example.sayakat_travel.service.impl;

import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.User;
import com.example.sayakat_travel.entity.enums.PostPlaceEventStatus;
import com.example.sayakat_travel.exceptions.ApiRequestException;
import com.example.sayakat_travel.repositories.PostLikesRepository;
import com.example.sayakat_travel.repositories.PostRepository;
import com.example.sayakat_travel.repositories.UserRepository;
import com.example.sayakat_travel.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikesRepository postLikesRepository;
    private final FileUploadServiceImpl fileUploadService;


    @Override
    public Post createPost(PostAndPlaceCreateDto postAndPlaceCreateDto){
        User user = userRepository.findById(postAndPlaceCreateDto.getUserId()).orElse(null);
        String photoUrl;
        try {
            photoUrl = fileUploadService.uploadFile(postAndPlaceCreateDto.getPhoto());
        } catch (IOException e) {
            throw new ApiRequestException("Can Not Upload a Image");
        }
        if(user == null){
            throw new ApiRequestException("User Not Found");
        }
        Post post = Post.builder()
                .title(postAndPlaceCreateDto.getTitle())
                .description(postAndPlaceCreateDto.getDescription())
                .status(PostPlaceEventStatus.AWAITS)
                .photo(photoUrl)
                .createdDate(LocalDate.now())
                .user(user)
                .build();
        return postRepository.save(post);
    }

    @Override
    public PostAndPlaceFindOneDto findPostById(UUID id) {
        Post post = postRepository.findById(id).orElse(null);
        if(post == null){
            throw new ApiRequestException("Place Not Found");
        }
        int likes = Objects.requireNonNull(postLikesRepository.findByPostId(Objects.requireNonNull(post).getId()).orElse(null)).size();
        return PostAndPlaceFindOneDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .user(post.getUser())
                .status(post.getStatus())
                .photo(post.getPhoto())
                .createdDate(post.getCreatedDate())
                .countLike(likes)
                .build();
    }

    @Override
    public Post updatePost(UUID id, PostAndPlaceUpdateDto post) {
        Post oldPost = postRepository.findById(id).orElse(null);
        if(oldPost == null){
            throw new ApiRequestException("Not Found Post:" + id);
        }else{
            if(post.getTitle() != null){
                oldPost.setTitle(post.getTitle());
            }
            if(post.getDescription() != null){
                oldPost.setDescription(post.getDescription());
            }
            if(post.getPhoto() != null){
                try {
                    String photoUrl = fileUploadService.uploadFile(post.getPhoto());
                    oldPost.setPhoto(photoUrl);
                } catch (IOException e) {
                    throw new ApiRequestException("Can Not Upload a Image");
                }
            }
            if(post.getStatus() != null){
                oldPost.setStatus(post.getStatus());
            }
        }
        return postRepository.save(oldPost);
    }

}
