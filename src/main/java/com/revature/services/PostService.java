package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.models.Post;
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
	
}
