package pl.bussystem.security.payment.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.bussystem.security.payment.model.dto.payload.BuyerPayload;
import pl.bussystem.security.payment.model.dto.payload.Payload;
import pl.bussystem.security.payment.model.dto.payload.ProductPayload;
import pl.bussystem.security.payment.model.dto.payload.SettingsPayload;
import pl.bussystem.security.payment.model.dto.response.Response;

import java.util.Arrays;

@RestController
@RequestMapping(value = "api/v1.0/payments")
public class PayController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<?> hello() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    headers.add("Authorization", "Bearer d9a4536e-62ba-4f60-8017-6053211d3f47");

    Payload payload = Payload.builder()
        .notifyUrl("https://your.eshop.com/notify")
        .customerIp("127.0.0.1")
        .merchantPosId("300746")
        .description("RTV market")
        .currencyCode("PLN")
        .totalAmount("21000")
        .buyer(
            BuyerPayload.builder()
                .email("john.doe@example.com")
                .phone("654111654")
                .firstName("John")
                .lastName("Doe")
                .language("pl")
                .build())
        .settings(
            SettingsPayload.builder()
                .invoiceDisabled(Boolean.TRUE.toString())
                .build())
        .products(
            Arrays.asList(
                ProductPayload.builder()
                    .name("Wireless Mouse for Laptop")
                    .unitPrice("15000")
                    .quantity("1")
                    .build(),
                ProductPayload.builder()
                    .name("HDMI cable")
                    .unitPrice("6000")
                    .quantity("1")
                    .build()))
        .build();

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<?> request = new HttpEntity<>(payload, headers);
    ResponseEntity<Response> responseEntity = restTemplate.postForEntity(API.API_URL, request, Response.class);

    return responseEntity;
  }
}
