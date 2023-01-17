package com.revature.advice;

import com.revature.models.*;
import com.revature.services.NotificationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class NotificationAspect {
	@Autowired
	NotificationService nServe;


	/* TODO: fix this based of like stuff
	@AfterReturning("execution(* com.revature.controllers.LikeController.createLike(..))")
	public void createLikeNotification(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		Like like = (Like) args[0];
		User sender = like.getUser();
		User recipient = like.getPost().getAuthor();
		Notification notification = new Notification(0, recipient, sender, like.getPost(), NotificationType.LIKE,
		NotificationStatus.UNREAD, "");
		nServe.createNotification(notification);
	}
	*/


	@AfterReturning(pointcut = "execution(* com.revature.services.PostService.upsert(..))", returning = "result")
	public void createCommentNotification(JoinPoint joinPoint, Object result) {

		//Object[] args = joinPoint.getArgs();
		//Post post = (Post) args[0];

		Post post = (Post) result;

		List<Post> comments = post.getComments();

		if(comments.size() > 0) {

			Post newPost = comments.get(comments.size() - 1);

			User sender = newPost.getAuthor();
			User recipient = post.getAuthor();

			Notification notification = new Notification(0L, recipient, sender, newPost, NotificationType.COMMENT,
					NotificationStatus.UNREAD, "");

			nServe.createNotification(notification);
		}
	}



	@AfterReturning(pointcut = "execution(* com.revature.services.UserService.followUser(..))", returning = "result")
	public void createFollowNotification(Object result) {

		Follow follow = (Follow) result;

		User sender = follow.getFollowed();
		User recipient = follow.getFollowing();

		Notification notification = new Notification(0L, recipient, sender, null, NotificationType.FOLLOW,
		NotificationStatus.UNREAD, "");

		nServe.createNotification(notification);
	}

}
