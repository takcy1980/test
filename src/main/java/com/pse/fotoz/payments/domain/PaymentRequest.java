/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.List;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author René
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {
    @JsonSerialize(using = ToStringSerializer.class)
    private Double amount;
    private String description;
    private String redirectUrl;
//    private String webhookUrl;
//    private String method;
//    private Map<String,String> metadata;
//    private String locale;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setAmount(int amount) {
        this.amount= Double.valueOf(amount);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

//    public String getWebhookUrl() {
//        return webhookUrl;
//    }
//
//    public void setWebhookUrl(String webhookUrl) {
//        this.webhookUrl = webhookUrl;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    public Map<String,String> getMetadata() {
//        return metadata;
//    }
//
//    public void setMetadata(Map<String,String> metadata) {
//        this.metadata = metadata;
//    }
//
//    public String getLocale() {
//        return locale;
//    }
//
//    public void setLocale(String locale) {
//        this.locale = locale;
//    }
    
    public LinkedMultiValueMap<String, String> toMap() {
        
        ObjectMapper m = new ObjectMapper();
    Map<String, List<String>> props = m.convertValue(this, Map.class);
      LinkedMultiValueMap<String, String> mm = new LinkedMultiValueMap<>(props);
    
        return mm;
    }
}
