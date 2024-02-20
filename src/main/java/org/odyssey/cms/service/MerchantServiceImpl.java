package org.odyssey.cms.service;

import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.User;
import org.odyssey.cms.exception.AccountException;
import org.odyssey.cms.repository.PaymentRequestRepository;
import org.odyssey.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PaymentRequestRepository paymentRequestRepository;

	@Override
	public User createNewMerchant(User newMerchant) throws AccountException {
		if (!newMerchant.getType().equals("merchant")){
			throw new AccountException("this user is not a merchant user");
		}

		Optional<User> accountOptional = this.userRepository.findById(newMerchant.getUserId());

		if (accountOptional.isPresent()) {
			throw new AccountException("user already exists\n" + accountOptional.get().toString());
		}

		return userRepository.save(newMerchant);
	}

	@Override
	public Boolean newRequest(Integer merchantId, Integer customerId,Double amount) throws AccountException {
		Optional<User> accountOptionalMerchant = this.userRepository.findById(merchantId);
		Optional<User> accountOptionalCustomer = this.userRepository.findById(customerId);
		if (accountOptionalMerchant.isEmpty()) {
			throw new AccountException("merchant Account doesn't exists: ");
		}
		else if (accountOptionalCustomer.isEmpty()) {
			throw new AccountException("merchant Account doesn't exists: ");
		}
		PaymentRequest paymentRequest=new PaymentRequest(0,merchantId,customerId,amount);
		this.paymentRequestRepository.save(paymentRequest);
		return true;
	}
}
