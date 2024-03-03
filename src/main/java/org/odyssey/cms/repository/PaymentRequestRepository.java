package org.odyssey.cms.repository;


import org.odyssey.cms.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Integer> {

}
