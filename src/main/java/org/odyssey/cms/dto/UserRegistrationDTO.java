package org.odyssey.cms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDTO {
	@NotNull
	private Integer userId;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String address;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Pattern(regexp = "^(?:(?:\\+|0{0,2})91(\\s*|[\\-])?|[0]?)?([6789]\\d{2}([ -]?)\\d{3}([ -]?)\\d{4})$")
	private String phone;
	@NotNull
	@NotBlank
	private String type;
	@NotNull
	@NotBlank
	private String status;
	@NotNull
	@NotBlank
	private String accountPassword;
}
