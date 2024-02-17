package org.odyssey.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoice {
    private Integer invoiceId;
    private String invoiceBody;
}
