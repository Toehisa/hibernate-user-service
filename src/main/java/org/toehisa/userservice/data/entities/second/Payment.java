package org.toehisa.userservice.data.entities.second;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;
import java.util.UUID;

//Способ наследования №2 - Одна таблица на иерархию

/**
 * Минусы
 * - Пустые ячейки
 *
 */

@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Включаем стратегию
@DiscriminatorColumn(name = "payment_type") // Колонка, где будет лежать маркер (CARD или CRYPTO)
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

    @Override
    public UUID getId() {
        return super.getId();
    }
}
