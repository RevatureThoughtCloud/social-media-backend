package com.revature.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Entity
@Data
public class PostLike {
	
	@EmbeddedId
	PostLikeKey id;
	
	@ManyToOne
	@MapsId("postId")
	@JoinColumn(name = "post_id")
	Post post;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	User user;
	
}
