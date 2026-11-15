package org.toehisa.userservice.data.entities.third;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class CardPayment extends Payment {

    @Column(name = "card_number")
    private String cardNumber;

    @Override
    public UUID getId() {
        return super.getId();
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
