package org.odyssey.cms.service;

import org.odyssey.cms.entity.Notification;
import org.odyssey.cms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class NotificationSeviceImpl implements NotificationService{
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> getAllNotification(Integer userId) {
		List<Notification> allNotification=notificationRepository.findByUserId(userId);
		return allNotification;
	}

	@Override
	public String deleteAllNotification(Integer userId) {
		if(notificationRepository.deleteByUserId(userId)){
			return "cleared All notification";
		}
		else{
			return "error while clearing";
		}
	}

	@Override
	public Notification getNotification(Integer notificationId)  {
		Optional<Notification> userNotification=notificationRepository.findById(notificationId);
		Notification notification= userNotification.get();
		return notification;
	}

	@Override
	public String deleteNotification(Integer notificationId){
		notificationRepository.deleteById(notificationId);
		Optional<Notification> userNotification=notificationRepository.findById(notificationId);
		if(userNotification.isEmpty()) {
			return "notification cleared";
		}
		else {
			return "error while clearing";
		}
	}

	@Override
	public Boolean saveNotification(Integer userId,String topic,String detail){
		Notification notification=new Notification(0,userId,topic,detail,LocalDateTime.now());
		notificationRepository.save(notification);
		return true;
//		List<NotificationDTO> allNotification=notificationRepository.findByUserId(userId);
//		if (allNotification.contains(notification)){
//			return true;
//		}
//		else {
//			throw new ("Error can't save");
//		}
	}
}
