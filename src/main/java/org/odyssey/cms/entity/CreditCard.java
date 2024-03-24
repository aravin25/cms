package org.odyssey.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cardId;
    @NotNull
    @NotBlank
    private String cardNumber;
    @NotNull
    private LocalDate expireDate;
    private Integer cvv;
    @NotNull
    private Double creditLimit;
    @NotNull
    private Double creditBalance;
    @NotNull
    @NotBlank
    private String activationStatus;
    @NotNull
    private Integer pinNumber;
    private Double interestRate = 0.018; // Interest on outstanding balance
    @NotNull
    @Pattern.List({ @Pattern(regexp = "MasterCard|Visa|AmEx|Discover", message = "Accepted values are MasterCard, Visa, AmEx, Discover")})
    private String vendor; // MasterCard, Visa, AmEx, Discover


    @OneToMany(mappedBy = "creditCard")
    @JsonManagedReference
    private List<Transaction> transactionList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="account")
    @JsonBackReference
    private Account account;
    public void addInterest() {
        creditBalance += (double) Math.round(100 * creditBalance * interestRate) / 100;
    }
}

