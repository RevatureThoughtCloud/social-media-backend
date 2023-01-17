package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository nRepo;

	public long countByUsername(String username) {
		return nRepo.countByRecipientUserNameAndStatus(username, NotificationStatus.UNREAD);
	}

	public Notification createNotification(Notification notification) {
		String message = "";
		switch(notification.getType()){
			case COMMENT:
				message = " has commented on your post!";
				break;
			case LIKE:
				message = " has liked post!";
				break;
			case FOLLOW:
				message = " has followed you!";
				break;
			default:
				message = "there seems to be an issue!";
		}
		notification.setMessage(message);

		return nRepo.save(notification);
	}
	@Transactional
	public boolean markNotificationRead(NotificationStatus status, long notificationId) {
		return nRepo.updateStatusById(status, notificationId) > 0;
	}

	public List<Notification> getNotificationsByUser(String username) {
		return nRepo.findAllByRecipientUserName(username);
	}

	public Optional<Notification> findNotificationById(long id){
		return nRepo.findById(id);
	}

	public void deleteNotification(long id){
		nRepo.deleteById(id);
	}


}
