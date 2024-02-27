package org.odyssey.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoice {
    @NotNull
    private Integer invoiceId;
    @NotNull
    @NotBlank
    private String invoiceBody;
}
