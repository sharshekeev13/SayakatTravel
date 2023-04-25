package com.example.sayakat_travel.repositories;

import com.example.sayakat_travel.entity.Post;
import com.example.sayakat_travel.entity.PostComment;
import com.example.sayakat_travel.entity.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Long> {

    Optional<ArrayList<PostComment>> findAllByPostAndCommentStatus(Post post, CommentStatus commentStatus);
}
