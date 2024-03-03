package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.NotificationException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;


@Service
public interface MerchantService {
	User createNewMerchant(User newMerchant)throws AccountException, NotificationException;
	Boolean newRequest(Integer paymentRequestId, Integer merchantId, Integer customerId, Double amount)throws AccountException,NotificationException;
	Invoice generateMerchantInvoice(Transaction transaction, PaymentRequest paymentRequest)throws UserException;
}
