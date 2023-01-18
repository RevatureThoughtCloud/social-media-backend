package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostLikeKey;
import com.revature.models.User;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeKey>{
	
	List<Post> findByUserId(int userId);
	
	List<User> findByPostId(int postId);
	
	Optional<PostLike> findById(PostLikeKey key);
	
}
