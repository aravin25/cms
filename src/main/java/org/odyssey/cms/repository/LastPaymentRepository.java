package org.odyssey.cms.repository;

import org.odyssey.cms.entity.LastPayment;
import org.odyssey.cms.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastPaymentRepository extends JpaRepository<LastPayment,Integer> {
	Optional<LastPayment> findByAccountId(Integer accountId);
}
