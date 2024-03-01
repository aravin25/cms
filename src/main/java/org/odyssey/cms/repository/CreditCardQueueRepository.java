package org.odyssey.cms.repository;

import org.odyssey.cms.entity.Admin;
import org.odyssey.cms.entity.CreditCardQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardQueueRepository extends JpaRepository<CreditCardQueue, Integer> {
}
