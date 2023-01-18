package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.repositories.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}