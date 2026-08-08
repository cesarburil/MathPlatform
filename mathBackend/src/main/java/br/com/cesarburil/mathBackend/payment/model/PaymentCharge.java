package br.com.cesarburil.mathBackend.payment.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCharge {
    private String id;
    private String reference_id;
    private String status;
    private Date created_at;

}
