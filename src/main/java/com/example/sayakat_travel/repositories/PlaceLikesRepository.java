package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PlaceLikesRepository extends JpaRepository<PlaceLikes, Long> {

    Optional<PlaceLikes> findByPlaceAndUser(Place place, User user);

    Optional<ArrayList<PlaceLikes>> findByPlaceId(UUID placeId);

}
