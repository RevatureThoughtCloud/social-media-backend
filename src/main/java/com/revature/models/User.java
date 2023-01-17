package com.revature.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy="user")
    @JsonIgnore
	private Set<PostLike> likes;
    

    @OneToMany(mappedBy = "followed", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followers = new LinkedList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followings = new LinkedList<>();

    public User(int id, String email, String password, String firstName, String lastName) {
    	this.id = id;
    	this.email = email;
    	this.password = password;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.likes = null;
    }

    public Long getFollowersCount() {
        return followings.stream().map(f -> f.getFollowing()).count();
    }

    public Long getFollowingsCount() {
        return followers.stream().map(f -> f.getFollowed()).count();
    }


}
