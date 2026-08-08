package br.com.cesarburil.mathBackend.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagBankWebhook {

    private String id;
    private String reference_id;
    private Date created_at;
    private String status;
    private List<PaymentCharge> charges;

}
