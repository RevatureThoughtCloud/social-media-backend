package com.revature.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@SuppressWarnings("serial")
@Embeddable
@Data
public class PostLikeKey implements Serializable{
	
	@Column(name="post_id")
	int postId;
	
	@Column(name="user_id")
	int userId;
}
