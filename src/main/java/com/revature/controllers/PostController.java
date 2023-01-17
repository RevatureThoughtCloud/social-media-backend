package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostLikeKey;
import com.revature.services.PostService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }
    
    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable int postId) {
    	Optional<Post> post = postService.getById(postId);
    	return ResponseEntity.ok(post.get());
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getAllTopPosts() {
        return ResponseEntity.ok(this.postService.getAllTop());
    }
    
    @GetMapping("/like/{postId}/{userId}")
    public ResponseEntity<Boolean> checkUserLikedPost(@PathVariable int postId, @PathVariable int userId){
    	return ResponseEntity.ok(this.postService.likeExists(new PostLikeKey(postId, userId)));
    }
    
    
    @PostMapping("/like")
    public ResponseEntity<PostLike> postNewLike(@RequestBody PostLike like) {
    	return ResponseEntity.ok(this.postService.insertLike(like));
    }
    
    @DeleteMapping("/like")
    public ResponseEntity<?> deleteLike(@RequestBody PostLike like) {
    	this.postService.deleteLike(like);
    	return new ResponseEntity<>(true, HttpStatus.OK);
    }
    

}
