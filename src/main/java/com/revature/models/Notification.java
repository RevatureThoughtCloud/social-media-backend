package com.revature.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notifications")
@EqualsAndHashCode
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
