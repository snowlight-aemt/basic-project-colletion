package me.snowlight.springkakaopayapi.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PaymentApiController {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kakao.api.key}")
    private String key;

    @GetMapping("/")
    public ResponseEntity<String> root() {
        log.info("ROOT");
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/payment")
    public ResponseEntity<String> payment() throws JsonProcessingException {
        PaymentDto.KakaoReadyRequest readyRequest = PaymentDto.KakaoReadyRequest.builder()
                    .cid("TC0ONETIME")
                .partner_order_id("122222")
                .partner_user_id("1122333")
                .item_name("TTTTTT")
                .quantity("1")
                .total_amount("2200")
//                .vat_amount("200")
                .tax_free_amount("0")
                .approval_url("http://localhost:8080/")
                .fail_url("http://localhost:8080/")
                .cancel_url("http://localhost:8080/")
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(objectMapper.convertValue(readyRequest, new TypeReference<>() {}));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "KakaoAK " + key);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://kapi.kakao.com/v1/payment/ready", HttpMethod.POST, body, String.class);
        log.info(exchange.getBody().toString());
        return exchange;
    }
}
