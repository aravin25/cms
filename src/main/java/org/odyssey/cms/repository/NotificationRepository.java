package org.odyssey.cms.repository;

import org.odyssey.cms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
	List<Notification> findByUserId(Integer userId);
	Boolean deleteByUserId(Integer userId);
}
