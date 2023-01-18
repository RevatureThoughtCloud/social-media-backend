package com.revature.repositories;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.models.User;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@DataJpaTest
@ActiveProfiles("test")
class NotificationRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private NotificationRepository repo;

	private Notification note;

	@BeforeEach
	void beforeEach(){

		User recipient = new User();
		recipient.setUserName("bob");

		recipient = entityManager.merge(recipient);

		note = new Notification();

		note.setType(NotificationType.LIKE);
		note.setStatus(NotificationStatus.UNREAD);

		note.setRecipient(recipient);




		entityManager.persist(note);
		entityManager.flush();

	}

	@AfterEach
	void clearData(){
		entityManager.clear();
	}

	@Test
	void countByRecipientUserNameAndStatus() {



		long result = repo.countByRecipientUserNameAndStatus("bob", NotificationStatus.UNREAD);

		assertEquals(1L, result);




	}

	@Test
	void findAllByRecipientUserName() {

		List<Notification> notes = new ArrayList<>();
		notes.add(note);

		List<Notification> results = repo.findAllByRecipientUserName("bob");


		assertEquals(notes, results);

	}

	@Test
	void updateStatusById() {

		Notification updatedNote = entityManager.find(Notification.class, 1L);

		repo.updateStatusById(NotificationStatus.READ, 1L);

		entityManager.refresh(updatedNote);


		assertEquals(NotificationStatus.READ, updatedNote.getStatus());


	}
}