package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("CARD") // То, что запишется в колонку payment_type
public class CardPayment extends Payment {

    public CardPayment(){}

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
