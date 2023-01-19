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
        dto.setFollowersCount(user.getFollowers().size());
        dto.setFollowingsCount(user.getFollowings().size());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setAboutMe(user.getAboutMe());
        return dto;
    }
}