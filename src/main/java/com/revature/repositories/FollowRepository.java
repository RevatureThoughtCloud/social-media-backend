package com.revature.repositories;

import com.revature.models.Follow;
import com.revature.models.User;
import com.revature.models.idclasses.FollowerId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowerId> {

    List<Follow> findByFollowingId(Integer id);

    List<Follow> findByFollowedId(Integer id);

    List<Follow> findByFollowingUserName(String id);

    List<Follow> findByFollowedUserName(String id);

    // Long countByFollowingId(Integer age);

    Long countByFollowedId(Integer age);

}
