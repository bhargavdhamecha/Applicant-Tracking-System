package com.trackingservices.service;


import com.trackingservices.ConstantStringPool;
import com.trackingservices.dto.EmailRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    private final String emailServiceUrl = ConstantStringPool.REST_EMAIL;
    public boolean sendEmail(EmailRequest emailRequest) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("to", emailRequest.getTo());
        body.put("subject", emailRequest.getSubject());
        body.put("message", emailRequest.getMessage());

        try {
            Object o = new RestTemplate().postForObject(emailServiceUrl, new HttpEntity<>(body, new HttpHeaders()), ResponseEntity.class).getStatusCode().is2xxSuccessful();
            return true;
        } catch (RestClientException e) {
            return false;
        }
    }
}