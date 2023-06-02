package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventLikesRepository extends JpaRepository<EventLikes,Long> {


    Optional<EventLikes> findByEventAndUser(Event event, User user);

    Optional<ArrayList<EventLikes>> findByEventId(UUID eventId);
}
