package org.toehisa.userservice.data.entities.third;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;

//Способ наследования №3 - Нормализация бд через join

/**
 * Минусы
 * - Пустые ячейки
 *
 */

@Entity(name = "Payment3")
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorColumn в JOINED не обязателен
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

    public BigDecimal getAmount() {
        return amount;
    }

    public User getUser() {
        return user;
    }
}
