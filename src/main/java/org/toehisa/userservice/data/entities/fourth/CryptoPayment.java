package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CRYPTO")// То, что запишется в колонку payment_type
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