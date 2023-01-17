package com.revature.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notifications")
public class Notification {

	@Id
	Long id;
	@ManyToOne
	User recipient;

	@ManyToOne
	User sender;

	@ManyToOne
	Post post;

	NotificationType type;

	NotificationStatus status;

	String message;
}
