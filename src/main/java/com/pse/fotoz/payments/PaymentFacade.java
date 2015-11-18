/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments;

import com.pse.fotoz.payments.domain.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author
 */
public class PaymentFacade {

    public static PaymentResponse GetPayment(String id) {
        Map<String, String> variables = new HashMap<String, String>(3);
        variables.put("id", id);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Authorization", "Bearer " + "1234");

        String PaymentGetUrl = "https://api.mollie.nl/v1/payments/{id}";
        RestTemplate restT = new RestTemplate();

        return null;

    }

    public static PaymentResponse CreatePayment(PaymentRequest payment) {

        
            System.setProperty("http.proxyHost", "127.0.0.1");
    System.setProperty("https.proxyHost", "127.0.0.1");
    System.setProperty("http.proxyPort", "8888");
    System.setProperty("https.proxyPort", "8888");
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
//        headers.add("Authorization", "Bearer " + "test_J297CVyPcaRM5s9UJQxDBFQQGh8VU7");
//        
        HttpHeaders httpHeaders = new HttpHeaders();
        ///httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Authorization", "Bearer " + "test_J297CVyPcaRM5s9UJQxDBFQQGh8VU7");
   httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        String paymentGetUrl = "https://api.mollie.nl/v1/payments";
        //String paymentGetUrl = "http://192.168.0.101:8080/v1/payments";
        RestTemplate restT = new RestTemplate();

        
//        //restT.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        restT.getMessageConverters().add(new FormHttpMessageConverter());
//        //HttpEntity<PaymentCreate> request = new HttpEntity<PaymentCreate>(payment, httpHeaders);
////HttpEntity<Map<String,String>> request = new HttpEntity<Map<String,String>>(payment.toMap(), httpHeaders);
        //HttpEntity<String> request = new HttpEntity<String>(null, httpHeaders);
        MultiValueMap<String, String>  map  = payment.toMap();
        
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("amount", "10.12");
//        map.add("description", "testdesc");
//        map.add("redirectUrl", "http://www.test.nl/redirect");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

//List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//messageConverters.add(new MappingJackson2HttpMessageConverter());    
//messageConverters.add(new FormHttpMessageConverter());
//restT.setMessageConverters(messageConverters);
        
        
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        LoggingRequestInterceptor loggerInterceptor = new LoggingRequestInterceptor();
        interceptors.add(loggerInterceptor);
        restT.setInterceptors(interceptors);

       // PaymentResponse result = restT.postForObject(paymentGetUrl, request, PaymentResponse.class);
        ResponseEntity<PaymentResponse> response  = restT.postForEntity(paymentGetUrl, request, PaymentResponse.class);
      
        return response.getBody();

    }

}
