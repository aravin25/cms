package org.odyssey.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreditCardUpdateDTO {
    @NotNull
    @NotBlank
    private Double creditLimit;
    @NotNull
    @NotBlank
    private Double creditBalance;
    @NotNull
    @NotBlank
    private String activationStatus;
    @NotNull
    private String pinNumber;
}
