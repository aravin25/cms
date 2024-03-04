package org.odyssey.cms.controller;

import org.odyssey.cms.entity.Notification;
import org.odyssey.cms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping("notifications/{userId}")
	public List<Notification> getAllUserNotification(@PathVariable Integer userId)  {
		return notificationService.getAllNotification(userId);
	}

	@DeleteMapping("notifications/{userId}")
	public String deleteAllUserNotification(@PathVariable Integer userId) {
		return notificationService.deleteAllNotification(userId);
	}

	@GetMapping("notification/{notificationId}")
	public Notification getNotificationByNotificationId(@PathVariable Integer notificationId) {
		return notificationService.getNotification(notificationId);
	}

	@DeleteMapping("notification/{notificationId}")
	public String deleteNotificationByNotificationId(@PathVariable Integer notificationId) {
		return notificationService.deleteNotification(notificationId);
	}

	@PostMapping("testing/{userId}/{topic}/{detail}")
	public Boolean testSave(@PathVariable Integer userId,@PathVariable String topic,@PathVariable String detail) {
		return notificationService.saveNotification(userId,topic,detail);
	}
}
