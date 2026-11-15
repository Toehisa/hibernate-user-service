package org.toehisa.userservice.data.entities.third;

import jakarta.persistence.*;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.math.BigDecimal;

//Способ наследования №3 - Нормализация бд через join

/**
 * Плюс - Идеальная структура БД. Нет пустых колонок, можно вешать NOT NULL ограничения на дочерние поля.
 * Минус - Деградация производительности. Если иерархия глубокая (4-5 уровней),
 *         Hibernate будет делать монструозные многоэтажные JOIN при каждом чтении.
 */

@Entity(name = "Payment3")
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
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
