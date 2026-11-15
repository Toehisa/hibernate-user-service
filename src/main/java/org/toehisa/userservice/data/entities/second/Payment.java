package org.toehisa.userservice.data.entities.second;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;

//Способ наследования №2 - Одна таблица на иерархию

/**
 * Плюс - Максимальная скорость. Всегда нужен только 1 SELECT без всяких джоинов.
 * Минус - Нарушение нормализации БД. Колонки дочерних классов (cardNumber, walletAddress)
 *         обязаны быть NULLable (допускать null) на уровне базы данных.
 */

@Entity(name = "Payment2")
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_type") // Колонка, где будет лежать маркер (CARD или CRYPTO)
public abstract class Payment extends BaseEntity {
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
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
