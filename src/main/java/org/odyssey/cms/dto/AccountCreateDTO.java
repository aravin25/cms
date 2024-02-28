package org.odyssey.cms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.odyssey.cms.entity.CreditCard;
import org.odyssey.cms.entity.User;

public class AccountCreateDTO {
    @NotNull
    private Integer accountId;
    @NotNull
    @Min(1000)
    private Double balance;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")
    private String password;


    @JsonManagedReference
    private User user;

    @JsonBackReference
    private CreditCard creditCard;
}
