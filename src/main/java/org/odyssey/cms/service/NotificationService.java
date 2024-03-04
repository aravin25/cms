package org.odyssey.cms.service;

import org.odyssey.cms.entity.Notification;
import org.odyssey.cms.exception.NotificationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
	public List<Notification> getAllNotification(Integer userId)throws NotificationException;
	public String deleteAllNotification(Integer userId)throws NotificationException;
	public Notification getNotification(Integer notificationId)throws NotificationException;
	public String deleteNotification(Integer notificationId)throws NotificationException;

	public Boolean saveNotification(Integer userId,String topic,String detail)throws NotificationException;
}
