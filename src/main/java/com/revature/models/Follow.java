package com.revature.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.IdClass;

import com.revature.models.idclasses.FollowerId;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FollowerId.class)
@Table(name = "follows", uniqueConstraints = @UniqueConstraint(columnNames = { "followed_id", "following_id" }))
public class Follow {

    // the user who is doing the following
    @Id
    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    // The user that is being followed
    @Id
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;

}
