package com.example.sayakat_travel.controller;

import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.*;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.service.PlaceCommentService;
import com.example.sayakat_travel.service.PostCommentService;
import com.example.sayakat_travel.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/place")
public class PlaceController {


    private final PlaceServiceImpl placeService;
    private final PlaceLikesServiceImpl placeLikesService;
    private final PlaceCommentService placeCommentService;

    @GetMapping()
    public ResponseEntity<List<Place>> getAll(){
        return ResponseEntity.ok(placeService.getAll());
    }
    @RequestMapping(method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Place> createPlace(@ModelAttribute PostAndPlaceCreateDto postAndPlaceCreateDto){
        return ResponseEntity.ok(placeService.createPlace(postAndPlaceCreateDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostAndPlaceFindOneDto> getPlaceById(@PathVariable UUID id){
        return ResponseEntity.ok(placeService.findPlaceById(id));
    }
    @RequestMapping(path = "/{id}",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Place> updatePlace(@PathVariable UUID id, @ModelAttribute PostAndPlaceUpdateDto place){
        return ResponseEntity.ok(placeService.updatePlace(id,place));
    }
    @PostMapping("/{placeId}/{userId}")
    public ResponseEntity<PlaceLikes> saveLikeToPost(@PathVariable UUID placeId, @PathVariable Long userId){
        return ResponseEntity.ok(placeLikesService.saveLikeToPost(placeId,userId));
    }
    @DeleteMapping("/{placeId}/{userId}")
    public ResponseEntity<String> deleteLike(@PathVariable UUID placeId,@PathVariable Long userId){
        placeLikesService.deleteLikeFromPost(placeId,userId);
        return ResponseEntity.ok("Deleted like from post " + placeId);
    }
    @GetMapping("/{id}/comment")
    public ResponseEntity<ArrayList<PlaceComment>> getAllCommentsByPlace(@PathVariable UUID id){
        return ResponseEntity.ok(placeCommentService.getAllCommentsByPlace(id));
    }

    @PostMapping("{id}/comment/create")
    public ResponseEntity<PlaceComment> createCommentToPost(@PathVariable UUID id,@RequestBody PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto){
        return ResponseEntity.ok(placeCommentService.saveNewCommentToPlace(postAndPlaceCommentCreateDto,id));
    }

    @PostMapping("/{id}/comment/{status}")
    public ResponseEntity<PlaceComment> updateCommentStatus(@PathVariable Long id, @PathVariable CommentStatus status){
        return ResponseEntity.ok(placeCommentService.updateStatus(id,status));
    }


}
