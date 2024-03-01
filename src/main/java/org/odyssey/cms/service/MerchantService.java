package org.odyssey.cms.service;

import org.odyssey.cms.dto.Invoice;
import org.odyssey.cms.dto.RequestInvoiceDTO;
import org.odyssey.cms.dto.UserRegistrationDTO;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.exception.PaymentRequestException;
import org.odyssey.cms.exception.UserException;
import org.springframework.stereotype.Service;


@Service
public interface MerchantService {
	User createNewMerchant(UserRegistrationDTO userRegistrationDTO) throws AccountException, UserException;
	Boolean newRequest(Integer paymentRequestId, Integer merchantId, Integer customerId, Double amount)throws AccountException;
	Invoice generateMerchantInvoice(RequestInvoiceDTO requestInvoiceDTO) throws UserException, PaymentRequestException;
}
