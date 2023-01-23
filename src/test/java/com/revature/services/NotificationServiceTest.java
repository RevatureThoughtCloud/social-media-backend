package com.revature.services;

import com.revature.models.*;
import com.revature.repositories.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {


	@Mock
	private NotificationRepository repo;

	@InjectMocks
	private NotificationService service;

	@Test
	void countByUsername() {
		when(repo.countByRecipientUserNameAndStatus("Bob", NotificationStatus.UNREAD)).thenReturn(5L);
		long result = service.countByUsername("Bob");
		assertEquals(5L, result);
		verify(repo, times(1)).countByRecipientUserNameAndStatus("Bob", NotificationStatus.UNREAD);

	}

	@Test
	void createNotification() {
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);
		when(repo.save(note)).thenReturn(note);
		Notification result = service.createNotification(note);
		assertEquals(note, result);
		verify(repo, times(1)).save(note);
	}

	@Test
	void generateMessageWhenCreateNotification(){
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);
		when(repo.save(note)).thenReturn(note);
		String result = service.createNotification(note).getMessage();
		assertEquals(" has liked your post!", result);
	}

	@Test
	void markNotificationRead() {
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);

		when(repo.updateStatusById(NotificationStatus.READ, 1L)).thenReturn(1);

		boolean result = service.markNotificationRead(NotificationStatus.READ, 1);

		assertTrue(result);

		verify(repo, times(1)).updateStatusById(NotificationStatus.READ, 1L);


	}

	@Test
	void getNotificationsByUser() {
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);
		List<Notification> notes = new ArrayList<>();
		notes.add(note);
		when(repo.findAllByRecipientUserName("bob")).thenReturn(notes);

		List<Notification> result = service.getNotificationsByUser("bob");

		assertEquals(notes, result);

		verify(repo, times(1)).findAllByRecipientUserName("bob");
	}

	@Test
	void getNotificationsByUserWithLimit(){
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);
		List<Notification> notes = new ArrayList<>();
		for(int i = 0; i < 10; i++){
		notes.add(note);
		}
		Page<Notification> page = new PageImpl<>(notes.subList(0, 4));
		when(repo.findAllByRecipientUserNameAndStatus("bob", NotificationStatus.UNREAD ,PageRequest.of(0, 5))).thenReturn(page);

		Page<Notification> result = service.getNotificationsByUserLimit5("bob");

		assertEquals(notes.subList(0, 4), result.getContent());

		verify(repo, times(1)).findAllByRecipientUserNameAndStatus("bob", NotificationStatus.UNREAD ,PageRequest.of(0, 5));
	}

	@Test
	void findNotificationById() {
		Notification note = new Notification();
		note.setType(NotificationType.LIKE);
		Optional<Notification> optionalNotification = Optional.of(note);
		when(repo.findById(1L)).thenReturn(optionalNotification);

		Optional<Notification> result = service.findNotificationById(1L);

		assertEquals(optionalNotification, result);

		verify(repo, times(1)).findById(1L);


	}

	@Test
	void deleteNotification() {
		service.deleteNotification(1L);
		verify(repo, times(1)).deleteById(1L);
	}

	@Test
	void testFindLikeNotification(){
		User recipient = new User();
		recipient.setId(1);
		User sender = new User();
		sender.setId(1);
		Post post = new Post();
		post.setId(1);

		Notification note = new Notification(1L, recipient, sender, post, NotificationType.LIKE, null, null);
		when(repo.findBySenderIdAndRecipientIdAndPostIdAndType(1, 1, 1, NotificationType.LIKE)).thenReturn(note);
		Notification result = service.findNotification(1, 1, 1, NotificationType.LIKE);
		assertEquals(note, result);
		verify(repo).findBySenderIdAndRecipientIdAndPostIdAndType(1, 1, 1, NotificationType.LIKE);
	}
	@Test
	void testFindFollowNotification(){
		User recipient = new User();
		recipient.setId(1);
		User sender = new User();
		sender.setId(1);
		Post post = new Post();
		post.setId(1);

		Notification note = new Notification(1L, recipient, sender, post, NotificationType.FOLLOW, null, null);

		when(repo.findBySenderIdAndRecipientIdAndType(1, 1,  NotificationType.FOLLOW)).thenReturn(note);
		Notification result = service.findNotification(1, 1,  NotificationType.FOLLOW);
		assertEquals(note, result);
		verify(repo).findBySenderIdAndRecipientIdAndType(1, 1,  NotificationType.FOLLOW);
	}

}