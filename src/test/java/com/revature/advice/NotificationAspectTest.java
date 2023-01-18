package com.revature.advice;

import com.revature.models.*;
import com.revature.services.NotificationService;
import com.revature.services.PostService;
import com.revature.services.UserService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NotificationAspectTest {


	@Mock
	private NotificationService notificationService;

	@Mock
	private PostService postService;

	@Mock
	private UserService userService;

	@InjectMocks
	private NotificationAspect notificationAspect;


	@Test
	void createLikeNotification() {

		PostLike like = new PostLike(null, new Post(1, null, null, 0, null, new User(), null, null), new User());

		when(notificationService.createNotification(any())).thenReturn(new Notification());

		doAnswer((invocation) -> {
			JoinPoint jp = mock(JoinPoint.class);
			notificationAspect.createLikeNotification(jp, like);
			return like;
		}).when(postService)
				.insertLike(like);

		postService.insertLike(like);

		verify(notificationService).createNotification(any());

	}

	@Test
	void createCommentNotification() {
		Post post = new Post();
		post.setComments(Arrays.asList(new Post(), new Post()));

		doAnswer((invocation) -> {
			JoinPoint jp = mock(JoinPoint.class);
			notificationAspect.createCommentNotification(jp, post);
			return post;
		}).when(postService)
				.upsert(post);

		postService.upsert(post);

		verify(notificationService).createNotification(any());
	}

	@Test
	void createFollowNotification() {
		User following = new User();
		following.setUserName("bobert");
		User follower = new User();
		follower.setUserName("bob");
		 Follow follow = new Follow(following, follower);

		when(notificationService.createNotification(any())).thenReturn(new Notification());

		doAnswer((invocation) -> {
			notificationAspect.createFollowNotification(follow);
			return follow;
		}).when(userService)
				.followUser(follower, following.getUserName());

		userService.followUser(follower, following.getUserName());

		verify(notificationService).createNotification(any());


	}
}