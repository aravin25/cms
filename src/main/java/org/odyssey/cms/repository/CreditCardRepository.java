package org.odyssey.cms.repository;

import org.odyssey.cms.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    Optional<CreditCard> findByCardNumber(String cardNumber);

    Optional<String> deleteByCardNumber(String cardNumber);

}
