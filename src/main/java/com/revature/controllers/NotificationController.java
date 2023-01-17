package com.revature.controllers;

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
@RequestMapping("/api/notifications")
public class NotificationController {
	@Autowired
	private NotificationService nServe;

	@GetMapping
	public ResponseEntity<List<Notification>> getNotifications(HttpSession session) {
		List<Notification> notifications = new ArrayList<>();
		try {
			User currentUser = (User) session.getAttribute("user");
			notifications = nServe.getNotificationsByUser(currentUser.getUserName());
		} catch(Exception e) {
			return new ResponseEntity<>(notifications, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(notifications, HttpStatus.ACCEPTED);
	}

	@GetMapping("/count")
	public ResponseEntity<Long> count(HttpSession session){
		long count = 0;
		try {
			User currentUser = (User) session.getAttribute("user");
			count = nServe.countByUsername(currentUser.getUserName());
		} catch(Exception e){
			return new ResponseEntity<>(count, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(count, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{id}")
	public boolean update(@PathVariable int id) {
		return nServe.markNotificationRead(NotificationStatus.READ, id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		nServe.deleteNotification(id);
	}


}
