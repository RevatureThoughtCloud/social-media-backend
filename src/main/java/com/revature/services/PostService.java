package com.revature.services;

import java.util.List;
import java.util.Optional;

import com.revature.models.PostType;
import com.revature.models.User;
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

	public List<Post> getAllByAuthorId(int id) {
		return postRepository.findAllByAuthorId(id);
	}

	public Optional<Post> getById(int postId) {
		return postRepository.findById(postId);
	}

	public PostLike insertLike(PostLike like) {
		Post likedPost = like.getPost();
		likedPost.setLikeCount(likedPost.getLikeCount() + 1);
		postRepository.save(likedPost);
		return postLikeRepository.save(like);
	}

	public void deleteLike(PostLike like) {
		Post likedPost = like.getPost();
		likedPost.setLikeCount(likedPost.getLikeCount() - 1);
		postRepository.save(likedPost);
		like.setId();
		postLikeRepository.delete(like);
	}

	public boolean likeExists(PostLikeKey key) {
		Optional<PostLike> exists = postLikeRepository.findById(key);
		return exists.isPresent();

	}

	public void deleteById(int postId) {
		postRepository.deleteById(postId);
	}
}
