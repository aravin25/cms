package org.odyssey.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPaymentRequestNotification {
	@NotNull
	Integer paymentRequestId;
	@NotNull
	Integer customerId;
	@NotNull
	Double amount;
}
