package com.revature.advice;

import com.revature.models.*;
import com.revature.repositories.UserRepository;
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
import java.util.Optional;

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

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private NotificationAspect notificationAspect;

	@Test
	void createLikeNotification() {

		PostLike like = new PostLike(null, new Post(1, null, null, 0, null, null, new User(), null, null), new User());

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
	void deleteLikeNotification() {

		PostLike like = new PostLike(null,
				new Post(1, null, null, 0, null, null, new User(1, null, null, null, null, null), null, null),
				new User(1, null, null, null, null, null));

		when(notificationService.findNotification(1, 1, 1, NotificationType.LIKE)).thenReturn(
				new Notification(1L, new User(1, null, null, null, null, null),
						new User(1, null, null, null, null, null),
						new Post(1, null, null, 0, null, null, new User(1, null, null, null, null, null), null,
								null),
						null, null, null));

		doAnswer((invocation) -> {
			JoinPoint jp = mock(JoinPoint.class);
			when(jp.getArgs()).thenReturn(new Object[] { like });
			notificationAspect.deleteLikeNotification(jp);
			return null;
		}).when(postService)
				.deleteLike(like);

		postService.deleteLike(like);

		verify(notificationService).deleteNotification(1L);

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

		// verify(notificationService).createNotification(any());
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

	@Test
	void deleteFollowNotification() {

		User following = new User();
		following.setId(1);
		following.setUserName("bobert");
		User follower = new User();
		follower.setUserName("bob");
		follower.setId(1);
		Follow follow = new Follow(following, follower);

		when(notificationService.findNotification(1, 1, NotificationType.FOLLOW))
				.thenReturn(new Notification(1L, new User(1, null, null, null, null, null),
						new User(1, null, null, null, null, null),
						new Post(1, null, null, 0, null, null, new User(1, null, null, null, null, null), null,
								null),
						null, null, null));

		when(userRepository.findByUserName("bobert")).thenReturn(Optional.of(following));

		doAnswer((invocation) -> {
			JoinPoint jp = mock(JoinPoint.class);
			when(jp.getArgs()).thenReturn(new Object[] { follower, "bobert" });
			notificationAspect.deleteFollowNotification(jp);
			return null;
		}).when(userService)
				.unFollowUser(follower, "bobert");

		userService.unFollowUser(follower, "bobert");

		verify(notificationService).deleteNotification(1L);

	}

}