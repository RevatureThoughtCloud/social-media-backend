package com.revature.advice;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.*;
import com.revature.repositories.UserRepository;
import com.revature.services.NotificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Aspect
@Component
public class NotificationAspect {
	@Autowired
	NotificationService nServe;
	@Autowired
	private UserRepository userRepository;

	@AfterReturning(pointcut = "execution(* com.revature.services.PostService.insertLike(..))", returning = "result")
	public void createLikeNotification(JoinPoint joinPoint, Object result) {

		PostLike like = (PostLike) result;
		User sender = like.getUser();
		User recipient = like.getPost().getAuthor();
		Notification notification = new Notification(0L, recipient, sender, like.getPost(), NotificationType.LIKE,
				NotificationStatus.UNREAD, "");
		nServe.createNotification(notification);

	}

	@AfterReturning("execution(* com.revature.services.PostService.deleteLike(..))")
	public void deleteLikeNotification(JoinPoint joinPoint) {
		PostLike like = (PostLike) joinPoint.getArgs()[0];
		Notification note = nServe.findNotification(like.getUser().getId(), like.getPost().getAuthor().getId(),
				like.getPost().getId(), NotificationType.LIKE);
		if (note == null) {
			return;
		}
		nServe.deleteNotification(note.getId());
		System.out.println("What we found: " + note);

	}

	@AfterReturning(pointcut = "execution(* com.revature.services.PostService.upsert(..))", returning = "result")
	public void createCommentNotification(JoinPoint joinPoint, Object result) {

		// Object[] args = joinPoint.getArgs();
		// Post post = (Post) args[0];

		Post post = (Post) result;

		List<Post> comments = post.getComments();

		if (comments.size() > 0) {

			Post newPost = comments.get(comments.size() - 1);
			User sender = newPost.getAuthor();
			User recipient = post.getAuthor();
			if (sender != recipient) {
				Notification notification = new Notification(0L, recipient, sender, newPost, NotificationType.COMMENT,
						NotificationStatus.UNREAD, "");

				nServe.createNotification(notification);
			}
		}
	}

	@AfterReturning(pointcut = "execution(* com.revature.services.UserService.followUser(..))", returning = "result")
	public void createFollowNotification(Object result) {

		Follow follow = (Follow) result;

		User sender = follow.getFollowing();
		User recipient = follow.getFollowed();

		Notification notification = new Notification(0L, recipient, sender, null, NotificationType.FOLLOW,
				NotificationStatus.UNREAD, "");

		nServe.createNotification(notification);
	}

	@AfterReturning("execution(* com.revature.services.UserService.unFollowUser(..))")
	public void deleteFollowNotification(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		User currentUser = (User) args[0];
		String recipientName = (String) args[1];

		try {
			User recipient = userRepository.findByUserName(recipientName)
					.orElseThrow(UserNotFoundException::new);
			Notification note = nServe.findNotification(currentUser.getId(), recipient.getId(),
					NotificationType.FOLLOW);
			nServe.deleteNotification(note.getId());
			System.out.println("What we found: " + note);
		} catch (Exception e) {
			System.out.println("Notification aspect:: No notification for this follow");
		}

	}

}
