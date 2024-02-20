package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
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
	Integer merchantId;
	Integer customerId;
	Double requestAmount;
}
