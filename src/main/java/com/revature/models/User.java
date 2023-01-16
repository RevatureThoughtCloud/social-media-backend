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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    
    @OneToMany(mappedBy="user")
    @JsonIgnore
	private Set<PostLike> likes;
    
    public User(int id, String email, String password, String firstName, String lastName) {
    	this.id = id;
    	this.email = email;
    	this.password = password;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.likes = null;
    }
}
