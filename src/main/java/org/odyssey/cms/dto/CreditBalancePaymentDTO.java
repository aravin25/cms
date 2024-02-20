package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditBalancePaymentDTO {
	private Integer accountId;
	private String password;
	private Double amount;
}
