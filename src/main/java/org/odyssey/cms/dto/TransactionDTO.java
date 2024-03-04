package org.odyssey.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.odyssey.cms.entity.CreditCard;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	@NotNull
	public Integer inputPin;
	@NotNull
	public Integer paymentRequestId;
}
