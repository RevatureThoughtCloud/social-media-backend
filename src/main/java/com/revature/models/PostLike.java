package com.revature.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {
	
	@EmbeddedId
	private PostLikeKey id = new PostLikeKey();
	
	@ManyToOne
	@MapsId("postId")
	@JoinColumn(name = "post_id")
	private Post post;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
}
