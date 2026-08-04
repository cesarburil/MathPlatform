package br.com.cesarburil.mathBackend.payment.controller;

import br.com.cesarburil.mathBackend.payment.model.PagBankWebhook;
import br.com.cesarburil.mathBackend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PaymentController {

    public final PaymentService paymentService;

    @PostMapping("/pay")
    public String pay(@RequestBody String encryptedCard) throws IOException {
        return paymentService.pay(encryptedCard);
    }

    @PostMapping("/webhook")
    public void webhook(@RequestBody PagBankWebhook pagBankWebhook) {
        paymentService.handleWebhook(pagBankWebhook);
    }

}
