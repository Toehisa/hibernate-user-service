package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
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