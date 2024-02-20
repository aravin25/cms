package org.odyssey.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private Integer cardNumber;
    private LocalDate expireDate;
    private Integer cvv;
    private Integer amount;
    private String activationStatus;
    private Double interestRate = 0.02;

//    @OneToOne
//    private Account account;
//    @OneToMany
//    private List<Transaction> transactionList = new ArrayList<>();
}
