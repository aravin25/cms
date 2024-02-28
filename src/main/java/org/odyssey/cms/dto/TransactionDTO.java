package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.odyssey.cms.entity.CreditCard;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
	String inputPin;
	Integer userId;
	CreditCard creditCard;
}
