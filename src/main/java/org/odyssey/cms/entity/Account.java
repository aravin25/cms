package org.odyssey.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    private Double balance;
    private LocalDate openDate;
    private String status;
    private String password;

    @OneToOne(mappedBy = "account")
    @JsonManagedReference
    private User user;
    @OneToOne
    @JoinColumn(name = "creditCard_id")
    @JsonBackReference
    private CreditCard creditCard;

}
