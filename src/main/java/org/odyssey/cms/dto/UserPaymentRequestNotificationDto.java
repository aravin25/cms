package org.odyssey.cms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentRequestNotificationDto {
	@NotNull
	Integer paymentRequestId;
	@NotNull
	Integer merchantId;
	@NotNull
	Double amount;
}
