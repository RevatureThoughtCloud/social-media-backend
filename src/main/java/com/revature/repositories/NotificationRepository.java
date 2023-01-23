package com.revature.repositories;

import com.revature.models.Notification;
import com.revature.models.NotificationStatus;
import com.revature.models.NotificationType;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	long countByRecipientUserNameAndStatus(String username, NotificationStatus status);

	Page<Notification> findAllByRecipientUserNameAndStatus(String username, NotificationStatus status, Pageable pageable);

	List<Notification> findAllByRecipientUserName(String username);

	Notification findBySenderIdAndRecipientIdAndPostIdAndType(int senderId, int recipientId, int postId, NotificationType type);
	Notification findBySenderIdAndRecipientIdAndType(int senderId, int recipientId, NotificationType type);

	@Modifying
	@Transactional
	@Query("UPDATE notifications n SET n.status = :status WHERE n.id=:id")
	int updateStatusById(@Param("status") NotificationStatus status, @Param("id") long id);

}
