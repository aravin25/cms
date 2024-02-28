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
    @NotBlank
    private Integer userId;
    @NotNull
    @NotBlank
    private String address;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^(?:(?:\\+|0{0,2})91(\\s*|[\\-])?|[0]?)?([6789]\\d{2}([ -]?)\\d{3}([ -]?)\\d{4})$")
    private String phone;

}
