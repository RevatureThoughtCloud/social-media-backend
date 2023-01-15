package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Authorized
    @GetMapping(value = "/followers")
    public ResponseEntity<List<User>> getFollowers(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        List<User> l = userService.getMyFollowers(currentUser.getId(),
                currentUser.getUserName());
        return new ResponseEntity<>(l, HttpStatus.ACCEPTED);
    }

    @Authorized
    @GetMapping(value = "/following")
    public ResponseEntity<List<User>> getFollowing(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        List<User> l = userService.getWhoImFollowing(currentUser.getId(),
                currentUser.getUserName());
        // List<User> m = new ArrayList<>();
        return new ResponseEntity<>(l, HttpStatus.ACCEPTED);
    }

    @Authorized
    @PostMapping(value = "/follow/{username}")
    public ResponseEntity follow(@PathVariable String username, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        userService.followUser(currentUser, username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Authorized
    @DeleteMapping(value = "/unfollow/{username}")
    public ResponseEntity unFollow(@PathVariable String username, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        userService.unFollowUser(currentUser, username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
