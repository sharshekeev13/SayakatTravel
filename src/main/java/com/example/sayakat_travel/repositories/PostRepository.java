package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post,UUID> {

}
