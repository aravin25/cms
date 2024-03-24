package org.odyssey.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {

    public Integer paymentRequestId;

    public String merchantName;

    LocalDateTime paymentRequestDate;

    String topic;

    String status;

    Double requestAmount;

}
