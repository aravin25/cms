package org.odyssey.cms.service;

import org.odyssey.cms.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
	public List<Notification> getAllNotification(Integer userId);
	public String deleteAllNotification(Integer userId);
	public Notification getNotification(Integer notificationId);
	public String deleteNotification(Integer notificationId);

	public Boolean saveNotification(Integer userId,String topic,String detail);
}
