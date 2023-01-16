package com.revature.dtos;

import com.revature.models.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserMapper {

    static public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setNumOfFollowers(user.getFollowers().size());
        dto.setNumOfFollowing(user.getFollowing().size());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}