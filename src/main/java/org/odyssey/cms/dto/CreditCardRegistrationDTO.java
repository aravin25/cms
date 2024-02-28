package org.odyssey.cms.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.odyssey.cms.entity.Account;

import java.time.LocalDate;

public class CreditCardRegistrationDTO {
    private Integer cardId;
    @NotNull
    @NotBlank
    private String cardNumber;
    @NotNull
    @NotBlank
    private LocalDate expireDate;
    private Integer cvv;
    @NotNull
    @NotBlank
    private Double creditLimit;
    @NotNull
    private String pinNumber;
    @JsonManagedReference
    private Account account;
}
