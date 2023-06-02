package com.example.sayakat_travel.controller;


import com.example.sayakat_travel.dto.request.EventCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceCommentCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceCreateDto;
import com.example.sayakat_travel.dto.request.PostAndPlaceUpdateDto;
import com.example.sayakat_travel.dto.response.EventFindOneDto;
import com.example.sayakat_travel.dto.response.PostAndPlaceFindOneDto;
import com.example.sayakat_travel.entity.*;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import com.example.sayakat_travel.service.EventCommentService;
import com.example.sayakat_travel.service.PlaceCommentService;
import com.example.sayakat_travel.service.impl.EventLikesServiceImpl;
import com.example.sayakat_travel.service.impl.EventServiceImpl;
import com.example.sayakat_travel.service.impl.PlaceLikesServiceImpl;
import com.example.sayakat_travel.service.impl.PlaceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/event")
public class EventController {



    private final EventServiceImpl eventService;
    private final EventLikesServiceImpl eventLikesService;
    private final EventCommentService eventCommentService;



    @GetMapping()
    public ResponseEntity<List<Event>> getAll(){
        return ResponseEntity.ok(eventService.getAll());
    }
    @RequestMapping(method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Event> createEvent(@ModelAttribute EventCreateDto eventCreateDto){
        return ResponseEntity.ok(eventService.createEvent(eventCreateDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventFindOneDto> getEventById(@PathVariable UUID id){
        return ResponseEntity.ok(eventService.findEventById(id));
    }
    @RequestMapping(path = "/{id}",method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Event> updateEvent(@PathVariable UUID id, @ModelAttribute PostAndPlaceUpdateDto place){
        return ResponseEntity.ok(eventService.updateEvent(id,place));
    }
    @PostMapping("/{placeId}/{userId}")
    public ResponseEntity<EventLikes> saveLikeToEvent(@PathVariable UUID placeId, @PathVariable Long userId){
        return ResponseEntity.ok(eventLikesService.saveLikeToEvent(placeId,userId));
    }
    @DeleteMapping("/{placeId}/{userId}")
    public ResponseEntity<String> deleteEvent(@PathVariable UUID placeId,@PathVariable Long userId){
        eventLikesService.deleteLikeFromEvent(placeId,userId);
        return ResponseEntity.ok("Deleted like from post " + placeId);
    }
    @GetMapping("/{id}/comment")
    public ResponseEntity<ArrayList<EventComment>> getAllCommentsByEvent(@PathVariable UUID id){
        return ResponseEntity.ok(eventCommentService.getAllCommentsByEvent(id));
    }

    @PostMapping("{id}/comment/create")
    public ResponseEntity<EventComment> createCommentToEvent(@PathVariable UUID id,@RequestBody PostAndPlaceCommentCreateDto postAndPlaceCommentCreateDto){
        return ResponseEntity.ok(eventCommentService.saveNewCommentToEvent(postAndPlaceCommentCreateDto,id));
    }

    @PostMapping("/{id}/comment/{status}")
    public ResponseEntity<EventComment> updateCommentStatus(@PathVariable Long id, @PathVariable CommentStatus status){
        return ResponseEntity.ok(eventCommentService.updateStatus(id,status));
    }

}
