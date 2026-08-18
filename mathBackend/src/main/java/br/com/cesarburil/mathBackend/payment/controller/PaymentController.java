package br.com.cesarburil.mathBackend.payment.controller;

import br.com.cesarburil.mathBackend.payment.model.PagBankWebhook;
import br.com.cesarburil.mathBackend.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody String encryptedCard) {
        return new ResponseEntity<>(paymentService.pay(encryptedCard), HttpStatus.ACCEPTED);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody PagBankWebhook pagBankWebhook) {
        paymentService.handleWebhook(pagBankWebhook);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        paymentService.addEmitter(emitter);
        return emitter;
    }

}
