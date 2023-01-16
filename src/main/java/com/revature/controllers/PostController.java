package com.revature.controllers;

import java.security.Principal;
import java.util.List;

import com.revature.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Post;
import com.revature.services.PostService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000", "http://p3-dist.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class PostController {

	private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
    	return ResponseEntity.ok(this.postService.getAll());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<Post>> getAllByAuthor(@PathVariable int id) {
        return ResponseEntity.ok(this.postService.getAllByAuthorId(id));
    }

    @Authorized
    @PutMapping
    public ResponseEntity<Post> upsertPost(@RequestBody Post post) {
    	return ResponseEntity.ok(this.postService.upsert(post));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getAllTopPosts() {
        return ResponseEntity.ok(this.postService.getAllTop());
    }

}
