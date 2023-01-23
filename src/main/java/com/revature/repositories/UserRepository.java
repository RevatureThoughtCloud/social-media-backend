package com.revature.repositories;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUserName(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    Optional<User> save(String username);

    List<User> findByFirstNameContainingIgnoreCaseOrUserNameContainingIgnoreCase(String name, String username);
}
