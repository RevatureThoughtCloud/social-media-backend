package com.revature.repositories;

import com.revature.models.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findAllByPostType(PostType postType);

}
