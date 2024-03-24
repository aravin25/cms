package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LastPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer lastPaymentId;
	@NotNull
	Integer accountId;
	@NotNull
	Double amount;
	@NotNull
	LocalDate date;
}
