package org.toehisa.userservice.data.entities.second;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CRYPTO")//Маркер делитель данных для дочерних классов чтобы не привязываться к имени класса и явно указать что инициализировать
public class CryptoPayment extends Payment {

    @Column(name = "wallet_address")
    private String walletAddress;

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}