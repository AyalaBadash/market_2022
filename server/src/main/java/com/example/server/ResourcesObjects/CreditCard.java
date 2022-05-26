package com.example.server.ResourcesObjects;

import com.example.server.businessLayer.ExternalComponents.Payment.PaymentMethod;

public class CreditCard implements PaymentMethod {
    private String cardNumber;
    private String expiredDate;
    private String threeDigits;

    public CreditCard(){};
    public CreditCard(String cardNumber, String expiredDate, String threeDigits) {
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
        this.threeDigits = threeDigits;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getThreeDigits() {
        return threeDigits;
    }

    public void setThreeDigits(String threeDigits) {
        this.threeDigits = threeDigits;
    }


}
