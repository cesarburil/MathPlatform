package br.com.cesarburil.mathBackend.payment.controller;

import br.com.cesarburil.mathBackend.payment.model.PagBankWebhook;
import br.com.cesarburil.mathBackend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        paymentService.addEmitter(emitter);
        return emitter;
    }

}
