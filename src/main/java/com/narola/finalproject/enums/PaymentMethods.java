package com.narola.finalproject.enums;

public enum PaymentMethods {

    CREDIT_CARD(1, "Credit card"),
    DEBIT_CARD(2, "Debit card"),
    NET_BANKING(3, "Net banking"),
    UPI(4, "UPI"),
    CASH_ON_DELIVERY(5, "Cash on delivery");

    private final int IdValue;
    private final String paymentName;

    PaymentMethods(int idValue, String paymentName) {
        IdValue = idValue;
        this.paymentName = paymentName;
    }

    public int getIdValue() {
        return IdValue;
    }

    public String getPaymentName() {
        return paymentName;
    }

}
