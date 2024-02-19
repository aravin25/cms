package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestNotificationDto {
	Integer merchantId;
	Double amount;
}
