package org.odyssey.cms.repository;

import org.odyssey.cms.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

    List<CreditCard> findAll();

}
