package com.revature.repositories;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	long countByRecipientUsernameAndStatus(String username, NotificationStatus status);

	List<Notification> findAllByRecipientUserName(String username);

	@Modifying
	@Transactional
	@Query("UPDATE notifications n SET n.status = :status WHERE n.id=:id")
	int updateStatusById(@Param("status") NotificationStatus status, @Param("id") long id);

}
