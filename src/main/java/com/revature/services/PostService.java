package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostLikeKey;
import com.revature.models.PostType;
import com.revature.repositories.PostLikeRepository;
import com.revature.repositories.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	private PostLikeRepository postLikeRepository;
	
	public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository) {
		this.postRepository = postRepository;
		this.postLikeRepository = postLikeRepository;
	}

	public List<Post> getAll() {
		return this.postRepository.findAll();
	}

	public Post upsert(Post post) {
		return this.postRepository.save(post);
	}

	public List<Post> getAllTop() {
		return postRepository.findAllByPostType(PostType.Top);
	}
	
	public PostLike insertLike(PostLike like) {
		return postLikeRepository.save(like);
	}
	
	public void deleteLike(PostLike like) {
		postLikeRepository.delete(like);
	}
	
	public boolean likeExists(PostLikeKey key) {
		Optional<PostLike> exists = postLikeRepository.findById(key);
		return exists.isPresent();
		
	}
}
