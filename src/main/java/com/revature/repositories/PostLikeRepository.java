package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostLikeKey;
import com.revature.models.User;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeKey>{
	
	List<Post> findByUserId(int userId);
	
	List<User> findByPostId(int postId);
	
	@Query("select case when count(p) > 0 then true else false end from PostLike p where post_id = :post and user_id = :user")
	boolean recordExists(@Param("post") int postId, @Param("user") int userId);
	
	int countByPostId(int postId);
}
