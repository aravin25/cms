package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationDTO {
	private Integer userId;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String type;
	private String status;

	private String accountPassword;
}
