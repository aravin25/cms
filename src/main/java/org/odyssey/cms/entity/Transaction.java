package org.odyssey.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionID;
    @NotNull
    @Min(100)
    private Double amount;
    @NotNull
    private LocalDateTime transactionDateTime;
    @NotNull
    private String topic;
    @NotNull
    private String merchant;
    @ManyToOne
    @JoinColumn(name = "creditCard_id")
    @JsonBackReference
    private CreditCard creditCard;
}
