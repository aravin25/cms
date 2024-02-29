package org.odyssey.cms.service;

import org.odyssey.cms.dto.MerchantPaymentRequestNotification;
import org.odyssey.cms.dto.UserPaymentRequestNotificationDto;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NotificationSeviceImpl implements NotificationService{
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;
	@Override
	public List<UserPaymentRequestNotificationDto> paymentNotificationUser(Integer customerId)throws NotificationException {
		List<UserPaymentRequestNotificationDto> customerPaymentRequest = this.paymentRequestRepository.findByCustomerId(customerId);
		if (customerPaymentRequest.isEmpty()) {
			throw new NotificationException("no request present");
		} else {
			return customerPaymentRequest;
		}
	}

	@Override
	public List<MerchantPaymentRequestNotification> paymentNotificatioMerchant(Integer merchantId) throws NotificationException {
		List<MerchantPaymentRequestNotification> merchantPaymentRequest = this.paymentRequestRepository.findByMerchantId(merchantId);
		if (merchantPaymentRequest.isEmpty()) {
			throw new NotificationException("no request present");
		} else {
			return merchantPaymentRequest;
		}
	}
}
