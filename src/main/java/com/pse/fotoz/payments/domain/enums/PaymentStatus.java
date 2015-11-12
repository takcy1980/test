/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *
 * @author René
 */
public enum PaymentStatus {

    OPEN("open"),
    CANCELLED("cancelled"),
    EXPIRED("expired"),
    PENDING("pending"),
    PAID("paid"),
    PAIDOUT("paidout"),
    REFUNDED("refunded"),
    CHARGEDBACK("charged_back");

    private String value;

    private PaymentStatus(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return this.value;
    }
}
