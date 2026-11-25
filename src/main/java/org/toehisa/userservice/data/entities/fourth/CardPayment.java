package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity(name = "FourthCardPayment")
public class CardPayment extends Payment {

    public CardPayment(){}

    @Column(name = "card_number")
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
