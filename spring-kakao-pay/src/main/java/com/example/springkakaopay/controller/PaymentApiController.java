package com.example.springkakaopay.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PaymentApiController {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/payment")
    public ResponseEntity<String> payment() throws JsonProcessingException {
        PaymentDto.KakaoReadyRequest readyRequest = PaymentDto.KakaoReadyRequest.builder()
                .cid("TC0ONETIME")
                .partner_order_id("partner_order_id")
                .partner_user_id("partner_user_id")
                .item_name("초코파이")
                .quantity(1)
                .total_amount(2200)
                .vat_amount(200)
                .tax_free_amount(0)
                .approval_url("http://localhost:8080")
                .fail_url("http://localhost:8080")
                .cancel_url("http://localhost:8080")
                .build();
        MultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        Map<String, String> params = objectMapper.convertValue(readyRequest, new TypeReference<Map<String, String>>() {});
        linkedMultiValueMap.setAll(params);
//        String jsonKakaoRequest = objectMapper.writerEn(readyRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        headers.set("Authorization", "KakaoAK 8bf1c6f271be1b28104f097ce127088e");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://kapi.kakao.com/v1/payment/ready", HttpMethod.POST, httpEntity, String.class);
        return exchange;
    }
}
