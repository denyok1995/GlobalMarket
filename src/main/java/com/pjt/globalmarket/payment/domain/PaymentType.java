package com.pjt.globalmarket.payment.domain;

public enum PaymentType {

    CARD("card"), COUPAY("coupay"), CASH("cash");

    private String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
