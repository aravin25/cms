package org.odyssey.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class AccountUpdateDTO {
    @Min(1000)
    private Double balance;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")
    private String password;
}
