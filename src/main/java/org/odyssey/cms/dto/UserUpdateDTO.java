package org.odyssey.cms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.odyssey.cms.entity.Account;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {
    @NotNull
    private Integer userId;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

}
