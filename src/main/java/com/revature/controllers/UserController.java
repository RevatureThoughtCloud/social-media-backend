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
import com.revature.dtos.UserDto;
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

    // Get followers
    @Authorized
    @GetMapping(value = "/{username}/followers")
    public ResponseEntity<List<UserDto>> getFollowers(@PathVariable String username, HttpSession session) {

        List<UserDto> l = userService.getMyFollowers(username);
        return new ResponseEntity<>(l, HttpStatus.ACCEPTED);
    }

    // Get following
    @Authorized
    @GetMapping(value = "/{username}/followings")
    public ResponseEntity<List<UserDto>> getFollowing(@PathVariable String username, HttpSession session) {
        // User currentUser = (User) session.getAttribute("user");
        List<UserDto> l = userService.getWhoImFollowing(username);

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
