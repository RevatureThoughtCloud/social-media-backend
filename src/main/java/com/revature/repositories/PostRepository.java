package com.revature.repositories;

import java.util.List;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;
import com.revature.models.PostType;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByPostType(PostType postType);
    
    List<Post> findAllByAuthorId(int id);
}