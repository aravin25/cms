package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.odyssey.cms.entity.PaymentRequest;
import org.odyssey.cms.entity.Transaction;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestInvoiceDTO {
    public Integer transactionID;
    public Integer paymentRequestID;
}
