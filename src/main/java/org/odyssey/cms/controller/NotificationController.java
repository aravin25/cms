package org.odyssey.cms.controller;

import org.odyssey.cms.dto.MerchantPaymentRequestNotification;
import org.odyssey.cms.dto.UserPaymentRequestNotificationDto;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("Notification")
@RestController
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@GetMapping("PaymentRequest/Customer/{ID}")
	public List<UserPaymentRequestNotificationDto> getPaymentNotificationCustomer(@PathVariable("ID") Integer customerID)throws NotificationException {
		return notificationService.paymentNotificationUser(customerID);
	}

	@GetMapping("PaymentRequest/Merchant/{ID}")
	public List<MerchantPaymentRequestNotification> getPaymentNotificationMerchant(@PathVariable("ID") Integer merchantID)throws NotificationException {
		return notificationService.paymentNotificatioMerchant(merchantID);
	}
}
