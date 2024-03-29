package org.odyssey.cms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Integer accountId;
    @NotNull
    @Min(1000)
    private Double balance;
    @NotNull
    private LocalDate openDate;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")
    private String password;
    @Pattern.List({ @Pattern(regexp = "sbi|icici|hdfc|boa|citi", message = "Accepted values are sbi, icici, hdfc, boa, citi")})
    private String bankType;
    @OneToOne(mappedBy = "account")
    @JsonManagedReference
    private User user;
    @OneToMany(mappedBy = "account")
    @JsonManagedReference
    private List<CreditCard> creditCards = new ArrayList<>();

}
