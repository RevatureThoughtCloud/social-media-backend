package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDto;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000",
        "http://localhost:8080" , "http://p3-dist.s3-website-us-east-1.amazonaws.com" }, allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get User by Id
    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id, HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        Optional<UserDto> user = userService.getUserById(id, currentUser.getId());

        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Update User details
    @Authorized
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserProfile(@PathVariable int id, @RequestBody UserDto updatedUser,
            HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser.getId() == id) {
            Optional<UserDto> updated = userService.updateUserProfile(id, updatedUser);
            if (updated.isPresent()) {
                return new ResponseEntity<>(updated.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Get User by Id
    @Authorized
    @GetMapping("/search/{id}")
    public ResponseEntity<List<UserDto>> getUserByText(@PathVariable String id, HttpSession session) {

        return new ResponseEntity<>(userService.getUserByText(id), HttpStatus.OK);
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