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

@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED) // Изменили только эту строку!
// @DiscriminatorColumn можно удалить, в JOINED он не обязателен
public abstract class Payment extends BaseEntity {
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Геттеры и сеттеры (amount, user)
}
