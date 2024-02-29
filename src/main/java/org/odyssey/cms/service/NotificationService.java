package org.odyssey.cms.service;

import org.odyssey.cms.dto.MerchantPaymentRequestNotification;
import org.odyssey.cms.dto.UserPaymentRequestNotificationDto;
import org.odyssey.cms.exception.NotificationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
	public List<UserPaymentRequestNotificationDto> paymentNotificationUser(Integer customerId)throws NotificationException;
	public List<MerchantPaymentRequestNotification> paymentNotificatioMerchant(Integer merchantId)throws NotificationException;
}
