/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 *  Test or real payment
 * @author René
 */
public enum PaymentMode {

    LIVE("live"),
    TEST("test");

    private String value;

    private PaymentMode(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
