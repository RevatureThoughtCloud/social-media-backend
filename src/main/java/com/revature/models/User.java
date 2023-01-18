package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User {

    public User(int i, String email2, String password2, String firstName2, String lastName2, String username2) {
        this.id = i;
        this.email = email2;
        this.password = password2;
        this.firstName = firstName2;
        this.lastName = lastName2;
        this.userName = username2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String userName;

    @OneToMany(mappedBy = "followed", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followers = new LinkedList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followings = new LinkedList<>();

    public Long getFollowersCount() {

        return followers.stream().map(f -> f.getFollowing()).count();
    }

    public Long getFollowingsCount() {
        return followings.stream().map(f -> f.getFollowed()).count();
    }

}
