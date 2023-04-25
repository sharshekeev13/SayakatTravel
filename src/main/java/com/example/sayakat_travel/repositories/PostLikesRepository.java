package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.PostLikes;
import com.example.sayakat_travel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {

    Optional<PostLikes> findByPostAndUser(Post post, User user);

    Optional<ArrayList<PostLikes>> findByPostId(UUID postId);

}
