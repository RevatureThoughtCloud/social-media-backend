package com.revature.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostLikeKey implements Serializable {

	@Column(name = "post_id")
	private Integer postId;

	@Column(name = "user_id")
	private Integer userId;

}
