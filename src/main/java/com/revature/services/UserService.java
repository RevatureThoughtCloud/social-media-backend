package com.revature.services;

import com.revature.annotations.Authorized;
import com.revature.dtos.UserDto;
import com.revature.dtos.UserMapper;
import com.revature.exceptions.AlreadyFollowingException;
import com.revature.exceptions.FollowingNotAllowedException;
import com.revature.exceptions.NoFollowingRelationshipExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Follow;
import com.revature.models.User;
import com.revature.models.idclasses.FollowerId;
import com.revature.repositories.FollowRepository;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public UserService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    // Get User by Id
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    // Update User details
    public Optional<UserDto> updateUserProfile(int id, UserDto updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User u = user.get();
            u.setUserName(updatedUser.getUserName());
            u.setFirstName(updatedUser.getFirstName());
            u.setLastName(updatedUser.getLastName());
            u.setAboutMe(updatedUser.getAboutMe());
            userRepository.save(u);
            return Optional.of(UserMapper.toDto(u));
        }
        return Optional.empty();
    }

    public Optional<UserDto> getUserById(int user2Id, int currentUserId) {

        Optional<User> user2 = userRepository.findById(user2Id);
        UserDto u2;
        if (user2.isPresent()) {
            u2 = UserMapper.toDto(user2.get());
            u2.setFollowedByCurrentUser(user2.get().isBeingFollowedBy(currentUserId));

        } else {
            throw new UserNotFoundException();
        }

        return Optional.of(u2);
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Authorized
    // currentUser request to follow user with 'username'
    @Transactional
    public Follow followUser(User currentUser, String username) {

        // Check if user not trying to follow herself
        if (currentUser.getUserName().equals(username)) {
            throw new FollowingNotAllowedException();
        }

        User followUser = userRepository.findByUserName(username)
                .orElseThrow(UserNotFoundException::new);

        User curr = userRepository.findByUserName(currentUser.getUserName())
                .orElseThrow(UserNotFoundException::new);

        // Check if already following
        Optional<Follow> foll = followRepository.findById(new FollowerId(followUser.getId(), currentUser.getId()));
        if (foll.isPresent())
            throw new AlreadyFollowingException();

        Follow f = new Follow(curr, followUser);
        followRepository.save(f);
        return f;
    }

    @Authorized
    // currentUser request to unfollow user with 'username'
    @Transactional
    public void unFollowUser(User currentUser, String username) {

        // Check if user not trying to unfollow herself
        if (currentUser.getUserName().equals(username)) {
            throw new FollowingNotAllowedException();
        }

        User followUser = userRepository.findByUserName(username)
                .orElseThrow(UserNotFoundException::new);

        User curr = userRepository.findByUserName(currentUser.getUserName())
                .orElseThrow(UserNotFoundException::new);

        // Check if existing follow relationship exists
        Follow foll = followRepository.findById(new FollowerId(followUser.getId(), currentUser.getId()))
                .orElseThrow(NoFollowingRelationshipExistsException::new);

        followRepository.delete(foll);

    }

    @Authorized
    // Gets userId's followers
    public List<UserDto> getMyFollowers(String username) {
        List<Follow> f = followRepository.findByFollowedUserName(username);

        return f.stream().map((user1) -> UserMapper.toDto(user1.getFollowing())).collect(Collectors.toList());

    }

    @Authorized
    // Gets who userId is following
    public List<UserDto> getWhoImFollowing(String username) {
        List<Follow> f = followRepository.findByFollowingUserName(username);

        return f.stream().map((user1) -> UserMapper.toDto(user1.getFollowed())).collect(Collectors.toList());
    }

    @Authorized
    // Gets who userId is following
    public List<UserDto> getUserByText(String searchText) {
        List<User> f = userRepository.findByFirstNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(searchText,
                searchText);

        return f.stream().map((user1) -> UserMapper.toDto(user1)).collect(Collectors.toList());
    }

}