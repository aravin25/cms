package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer paymentRequestId;
	@NotNull
	Integer merchantId;
	@NotNull
	Integer customerId;
	@NotNull
	@Min(100)
	Double requestAmount;
}
