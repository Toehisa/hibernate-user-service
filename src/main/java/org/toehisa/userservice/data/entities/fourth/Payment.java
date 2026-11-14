package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment extends BaseEntity {
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setAmount(BigDecimal bigDecimal) {

    }

    public void setUser(User user) {
        this.user = user;
    }

    // Геттеры и сеттеры (amount, user)
}
