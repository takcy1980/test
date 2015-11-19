/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pse.fotoz.payments.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;


/***
 * Locales supported by Mollie
 * @author René 
 */
public enum Locale {

    GERMANY("de"),
    ENGLAND("en"),
    SPAIN("es"),
    FRANCE("fr"),
    BELGIUM("be"),
    BELGIUM_FRENCH("be-fr"),
    NETHERLANDS("nl");

    private String value;

    private Locale(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
