package br.com.cesarburil.mathBackend;

import br.com.cesarburil.mathBackend.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MathBackendApplicationTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    void contextLoads() {
        assertThat(paymentService).isNotNull();
    }
}
