package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class RequestInvoiceDTO {
    public Integer transactionID;
    public Integer paymentRequestID;
}
