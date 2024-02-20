package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
<<<<<<< Updated upstream
=======
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer paymentRequestId;
>>>>>>> Stashed changes
	Integer merchantId;
	Integer customerId;
	Double requestAmount;
}
