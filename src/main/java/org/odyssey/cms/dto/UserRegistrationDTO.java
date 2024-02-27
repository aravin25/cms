package org.odyssey.cms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
	@Min(10)
	@Max(10)
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
