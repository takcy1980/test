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
public enum PaymentMethod {

    IDEAL("ideal"),
    CREDITCARD("creditcard"),
    MISTERCASH("mistercash"),
    SOFORT("sofort"),
    BANKTRANSFER("banktransfer"),
    DIRECTDEBIT("directdebit"),
    BELFIUS("belfius"),
    PAYPAL("paypal"),
    BITCOIN("bitcoin"),
    PODIUMCADEAUKAART("podiumcadeaukaart"),
    PAYSAFECARD("paysafecard");

    private String value;

    private PaymentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
