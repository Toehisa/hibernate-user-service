package org.toehisa.userservice.data.entities.second;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
@DiscriminatorValue("CARD")//Маркер делитель данных для дочерних классов чтобы не привязываться к имени класса и явно указать что инициализировать
public class CardPayment extends Payment {

    @Column(name = "card_number")
    private String cardNumber;

    @Override
    public UUID getId() {
        return super.getId();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
