package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User {

    public User(String email2, String password2, String firstName2, String lastName2, String username2) {
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
    private String aboutMe = "About Me";

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<PostLike> likes;

    @OneToMany(mappedBy = "followed", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followers = new LinkedList<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Follow> followings = new LinkedList<>();

    public User(int id, String email, String password, String firstName, String lastName, String userName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.likes = null;
        this.userName = userName;
    }

    public Long getFollowersCount() {

        return followers.stream().map(f -> f.getFollowing()).count();
    }

    public Long getFollowingsCount() {
        return followings.stream().map(f -> f.getFollowed()).count();
    }

    public boolean isBeingFollowedBy(int userId) {
        return followers.stream().anyMatch(x -> x.getFollowing().getId() == userId);
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /*
         * Check if o is an instance of Complex or not
         * "null instanceof [type]" also returns false
         */
        if (!(o instanceof User)) {
            return false;
        }

        // Compare the data members and return accordingly
        return this.id == ((User) o).id;
    }

    @Override
    public int hashCode() {

        // We are returning the Geek_id
        // as a hashcode value.
        // we can also return some
        // other calculated value or may
        // be memory address of the
        // Object on which it is invoked.
        // it depends on how you implement
        // hashCode() method.
        return this.id;
    }

}
