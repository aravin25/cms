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
<<<<<<< Updated upstream
    private Double creditLimit;
    private Double creditBalance;
=======
//    private Integer amount;
>>>>>>> Stashed changes
    private String activationStatus;
    private Double creditLimit;
    private Double creditBalance;

<<<<<<< Updated upstream
    ////@OneToOne
    //private Account account;
    @OneToMany
    private List<Transaction> transactionList = new ArrayList<>();
=======
//    @OneToOne
//    private Account account;
//    @OneToMany
//    private List<Transaction> transactionList = new ArrayList<>();
>>>>>>> Stashed changes
}
