package org.odyssey.cms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.odyssey.cms.entity.Notification;
import org.odyssey.cms.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotificationTest {
	@Mock
	private NotificationRepository notificationRepository;
	@InjectMocks
	private NotificationSeviceImpl notificationSevice;

	private List<Notification> allNotification;
	private List<Notification> checkAllNotification;

	Notification notification1;

	@BeforeEach
	public void setUp(){
		notification1=new Notification(1,1,"test1","detail1", LocalDateTime.now());
		Notification notification2=new Notification(2,2,"test2","detail2", LocalDateTime.now());
		allNotification= Arrays.asList(notification1,notification2);
	}
	@Test
	public void getAllNotificationTest(){
		Mockito.when(notificationRepository.findByUserId(1)).thenReturn(allNotification);
		checkAllNotification=notificationSevice.getAllNotification(1);
		Assertions.assertEquals(checkAllNotification,allNotification);
	}
	@Test
	public void deleteAllNotificationTest(){
		Mockito.when(notificationRepository.deleteByUserId(1)).thenReturn(true);
		Assertions.assertEquals("cleared All notification",notificationSevice.deleteAllNotification(1));
	}

	@Test
	public void getNotificationTest(){
		Mockito.when(notificationRepository.findById(1)).thenReturn(Optional.of(notification1));
		Notification checkNotification=notificationSevice.getNotification(1);
		Assertions.assertEquals(checkNotification,notification1);
	}

//	@Test
//	public void saveNotificationTest(){
//		LocalDateTime localDateTime=LocalDateTime.now();
//		Notification notification=new Notification(0,0,"test1","detail1",localDateTime);
//		Mockito.when(notificationRepository.save(notification)).thenReturn(notification);
//		Assertions.assertEquals(true,notificationSevice.saveNotification(0,"test1","detail1"));
//	}


}
