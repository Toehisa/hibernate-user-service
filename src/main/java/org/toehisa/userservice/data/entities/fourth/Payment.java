package org.toehisa.userservice.data.entities.fourth;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;

//Способ наследования №4 - Каждому по таблице

/**
 * Плюс - Идеально быстро достает конкретный подкласс (один простой SELECT).
 * Минус - Катастрофа при полиморфных запросах.
 *         На больших объемах данных UNION убивает производительность базы.
 */

@Entity(name = "Payment4")
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment extends BaseEntity {
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }

    public void setAmount(BigDecimal bigDecimal) {

    }

    public void setUser(User user) {
        this.user = user;
    }
}
