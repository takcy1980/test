/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pse.fotoz.payments.domain.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author
 */
public class PaymentFacade {

    private boolean isDebug;
    private boolean useProxy;

    public PaymentResponse GetPayment(String id) {
        Map<String, String> variables = new HashMap<String, String>(3);
        variables.put("id", id);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Bearer " + "1234");

        String PaymentGetUrl = "https://api.mollie.nl/v1/payments/{id}";
        RestTemplate restT = new RestTemplate();

        return null;

    }

    /**
     * *
     * Creates a payment
     *
     * @param payment request parameters to be sent
     * @return the response object, can be empty
     * @throws RestClientException in case of malformed requests(incorrect
     * request data gives 422, server unreachable, etc)
     */
    public Optional<PaymentResponse> CreatePayment(PaymentRequest payment) throws RestClientException {
        String paymentGetUrl = "https://api.mollie.nl/v1/payments";

        if (this.useProxy) {
            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("https.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "8888");
            System.setProperty("https.proxyPort", "8888");
            paymentGetUrl = "http://192.168.0.101:8080/v1/payments";
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + "test_J297CVyPcaRM5s9UJQxDBFQQGh8VU7");
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restT = new RestTemplate();

        if (isDebug) {
            List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
            LoggingRequestInterceptor loggerInterceptor = new LoggingRequestInterceptor();
            interceptors.add(loggerInterceptor);
            restT.setInterceptors(interceptors);
        }
        ResponseEntity<PaymentResponse> response;
        try {
            MultiValueMap<String, String> map = payment.toMap();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);
            response = restT.postForEntity(paymentGetUrl, request, PaymentResponse.class);
        } catch (RestClientException e) {
            throw e;
        }

        return Optional.ofNullable(response.getBody());

    }

    /**
     * @return is debugging enabled
     */
    public boolean isIsDebug() {
        return isDebug;
    }

    /**
     * @param isDebug sets debug mode: intercept the requests made and output to
     * console
     */
    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * @return is using of localhost proxy enabled
     */
    public boolean isUseProxy() {
        return useProxy;
    }

    /**
     * @param useProxy sets the use of localhost proxy for debugging purposes
     */
    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

}
