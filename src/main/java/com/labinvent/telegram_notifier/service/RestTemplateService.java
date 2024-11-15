package com.labinvent.telegram_notifier.service;

import com.labinvent.telegram_notifier.dto.EurekaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RestTemplateService {

    public String get(String request) {
        String[] split = request.split("\\|");
        String environment = split[0];
        String url = split[1];

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        params.put("Accept", "application/json");

        ResponseEntity<EurekaResponse> response
                = restTemplate.getForEntity(url, EurekaResponse.class, params);

        return  "<b>%s</b>\n%s".formatted(environment.toUpperCase(), Objects.requireNonNull(response.getBody()).message());
    }
}
