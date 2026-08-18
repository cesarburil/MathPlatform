package br.com.cesarburil.mathBackend.payment.controller;

import br.com.cesarburil.mathBackend.payment.model.PagBankWebhook;
import br.com.cesarburil.mathBackend.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Tag(name = "payment", description = "Controller to process payments and webhooks")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    @Operation(summary = "Start payment", description = "Send encrypted card to PagBank")
    @ApiResponse(responseCode = "202", description = "Payment request accepted")
    @ApiResponse(responseCode = "401", description = "User is not authenticated")
    @ApiResponse(responseCode = "502", description = "Payment provider error")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<String> pay(@RequestBody String encryptedCard) {
        return new ResponseEntity<>(paymentService.pay(encryptedCard), HttpStatus.ACCEPTED);
    }

    @PostMapping("/webhook")
    @Operation(summary = "PagBank webhook", description = "Receive payment status from PagBank")
    @ApiResponse(responseCode = "204", description = "Webhook processed")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> webhook(@RequestBody PagBankWebhook pagBankWebhook) {
        paymentService.handleWebhook(pagBankWebhook);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Payment SSE", description = "Subscribe to payment events")
    @ApiResponse(responseCode = "200", description = "SSE stream opened")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        paymentService.addEmitter(emitter);
        return emitter;
    }

}
