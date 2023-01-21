package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private int followersCount;
    private int followingsCount;
    private boolean followedByCurrentUser;
    private String aboutMe;
}
