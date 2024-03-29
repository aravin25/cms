package org.odyssey.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditBalancePaymentDTO {
	@NotNull
	private Integer accountId;
	@NotNull
	@NotBlank
	private String password;
	@Min(0)
	private Double amount;
}
