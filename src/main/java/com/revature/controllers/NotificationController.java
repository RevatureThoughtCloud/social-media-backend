package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.User;
import com.revature.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000",
		"http://p3-dist.s3-website-us-east-1.amazonaws.com/" }, allowCredentials = "true", allowedHeaders = "*")
public class NotificationController {
	@Autowired
	private NotificationService nServe;

	@Authorized
	@GetMapping
	public ResponseEntity<List<Notification>> getNotifications(HttpSession session) {
		List<Notification> notifications = new ArrayList<>();
		try {
			User currentUser = (User) session.getAttribute("user");
			notifications = nServe.getNotificationsByUser(currentUser.getUserName());
		} catch (Exception e) {
			return new ResponseEntity<>(notifications, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
	}

	@Authorized
	@GetMapping("/nav")
	public ResponseEntity<List<Notification>> getNotificationsWithLimit(HttpSession session) {
		List<Notification> notifications = new ArrayList<>();
		try {
			User currentUser = (User) session.getAttribute("user");
			notifications = nServe.getNotificationsByUserLimit5(currentUser.getUserName()).getContent();
		} catch(Exception e) {
			return new ResponseEntity<>(notifications, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
	}

	@Authorized
	@GetMapping("/count")
	public ResponseEntity<Long> count(HttpSession session) {
		long count = 0;
		try {
			User currentUser = (User) session.getAttribute("user");
			count = nServe.countByUsername(currentUser.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(count, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(count, HttpStatus.ACCEPTED);
	}

	@Authorized
	@PutMapping("/{id}")
	public boolean update(@PathVariable int id) {
		return nServe.markNotificationRead(NotificationStatus.READ, id);
	}

	@Authorized
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		nServe.deleteNotification(id);
	}

}
