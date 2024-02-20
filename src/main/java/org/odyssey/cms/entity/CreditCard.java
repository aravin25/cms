package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String cardNumber;
    private LocalDate expireDate;
    private Integer cvv;
    private Double creditLimit;
    private Double creditBalance;
    private String activationStatus;
    private String pinNumber;
    private Double interestRate = 0.02; // Interest on outstanding balance

    @OneToOne
    private Account account;
    @OneToMany
    private List<Transaction> transactionList = new ArrayList<>();

    public void addInterest() {
        creditBalance += creditBalance * interestRate;
    }
}

