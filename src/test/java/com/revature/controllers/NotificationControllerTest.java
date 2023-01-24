package com.revature.controllers;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import com.revature.models.User;
import com.revature.services.NotificationService;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NotificationService service;

	private Notification note;

	private User recipient;

	private MockHttpSession session;

	@BeforeEach
	void createUserAndNote() {
		note = null;
		recipient = null;
		session = null;


		recipient = new User();

		recipient.setUserName("bob");

		note = new Notification();

		note.setStatus(NotificationStatus.UNREAD);
		note.setType(NotificationType.LIKE);
		note.setRecipient(recipient);

		session = new MockHttpSession();
		session.setAttribute("user", recipient);

	}

	@Test
	void getNotifications() throws Exception {

		when(service.getNotificationsByUser("bob")).thenReturn(List.of(note));

		mockMvc.perform(get("/notifications").session(session))
				.andExpect(status().isAccepted())
				.andExpect(content().json(
						"[{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}]"));

	}

	void testGetWithLimit() throws Exception {
		List<Notification> notes = new ArrayList<>();
		for(int i = 0; i < 10; i++){
			notes.add(note);
		}
		when(service.getNotificationsByUser("bob")).thenReturn(notes);


		mockMvc.perform(get("/notifications").session(session))
				.andExpect(status().isAccepted())
				.andExpect(content().json(
						"[{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}," +
								"{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}," +
								"{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}," +
								"{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}," +
								"{\"id\":null,\"recipient\":{\"id\":0,\"email\":null,\"firstName\":null,\"lastName\":null,"
								+ "\"userName\":\"bob\",\"followingsCount\":0,\"followersCount\":0},\"sender\":null,"
								+ "\"post\":null,\"type\":\"LIKE\",\"status\":\"UNREAD\",\"message\":null}]"));
	}

	@Test
	void count() throws Exception {
		when(service.countByUsername("bob")).thenReturn(1L);

		mockMvc.perform(get("/notifications/count").session(session))
				.andExpect(status().isAccepted())
				.andExpect(content().json("1"));

	}

	@Test
	void testUpdate() throws Exception {

		when(service.markNotificationRead(NotificationStatus.READ, 1)).thenReturn(true);

		mockMvc.perform(put("/notifications/1").session(session))
				.andExpect(status().isOk())
				.andExpect(content().string("true"));

	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/notifications/1").session(session))
				.andExpect(status().isOk());
	}
}