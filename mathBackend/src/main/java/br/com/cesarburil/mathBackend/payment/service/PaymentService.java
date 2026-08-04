package br.com.cesarburil.mathBackend.payment.service;

import br.com.cesarburil.mathBackend.auth.model.User;
import br.com.cesarburil.mathBackend.auth.model.UserRole;
import br.com.cesarburil.mathBackend.auth.service.UserService;
import br.com.cesarburil.mathBackend.payment.model.PagBankWebhook;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final UserService userService;

    @Value("${pagbank.token}")
    private String pagbankToken;

    public String pay(String encryptedCard) throws IOException {

        String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "{\n" +
                        "            \"reference_id\": \""+ username +"\",\n" +
                        "            \"customer\": {\n" +
                        "        \"name\": \"Jose da Silva\",\n" +
                        "                \"email\": \"email@test.com\",\n" +
                        "                \"tax_id\": \"12345678909\",\n" +
                        "                \"phones\": [\n" +
                        "        {\n" +
                        "            \"country\": \"55\",\n" +
                        "                \"area\": \"11\",\n" +
                        "                \"number\": \"999999999\",\n" +
                        "                \"type\": \"MOBILE\"\n" +
                        "        }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "            \"items\": [\n" +
                        "    {\n" +
                        "        \"reference_id\": \"referencia do item\",\n" +
                        "            \"name\": \"nome do item\",\n" +
                        "            \"quantity\": 1,\n" +
                        "            \"unit_amount\": 500\n" +
                        "    }\n" +
                        "    ],\n" +
                        "            \"shipping\": {\n" +
                        "        \"address\": {\n" +
                        "            \"street\": \"Avenida Brigadeiro Faria Lima\",\n" +
                        "                    \"number\": \"1384\",\n" +
                        "                    \"complement\": \"apto 12\",\n" +
                        "                    \"locality\": \"Pinheiros\",\n" +
                        "                    \"city\": \"São Paulo\",\n" +
                        "                    \"region_code\": \"SP\",\n" +
                        "                    \"country\": \"BRA\",\n" +
                        "                    \"postal_code\": \"01452002\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "            \"notification_urls\": [\n" +
                        "            \"https://ac6b-2804-14d-aea0-8aa0-3869-d29f-3b85-acc9.ngrok-free.app/webhook\"\n" +
                        "            ],\n" +
                        "            \"charges\": [\n" +
                        "    {\n" +
                        "        \"reference_id\": \"referencia da cobranca\",\n" +
                        "            \"description\": \"descricao da cobranca\",\n" +
                        "            \"amount\": {\n" +
                        "        \"value\": 500,\n" +
                        "                \"currency\": \"BRL\"\n" +
                        "    },\n" +
                        "        \"payment_method\": {\n" +
                        "        \"type\": \"CREDIT_CARD\",\n" +
                        "                \"installments\": 1,\n" +
                        "                \"capture\": true,\n" +
                        "                \"card\": {\n" +
                        "            \"encrypted\":\"" + encryptedCard + "\",\n" +
                        "                    \"store\": false\n" +
                        "        },\n" +
                        "        \"holder\": {\n" +
                        "            \"name\": \"Jose da Silva\",\n" +
                        "                    \"tax_id\": \"65544332211\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "    }\n" +
                        "    ]\n" +
                        "}");

        Request request = new Request.Builder()
                .url("https://sandbox.api.pagseguro.com/orders")
                .post(body)
                .addHeader("Authorization", "Bearer ".concat(pagbankToken))
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }


    public void handleWebhook(PagBankWebhook pagBankWebhook) {

        if (!pagBankWebhook.getReference_id().isEmpty() && Objects.equals(pagBankWebhook.getCharges().getFirst().getStatus(), "PAID")) {
            User user = userService.findByUsername(pagBankWebhook.getReference_id());
            user.setRole(UserRole.PREMIUM);
            userService.updateUser(user);

        }

    }
}
