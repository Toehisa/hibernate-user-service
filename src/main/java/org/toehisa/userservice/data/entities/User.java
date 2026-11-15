package org.toehisa.userservice.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.toehisa.userservice.data.entities.first.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@AttributeOverride(name = "id", column = @Column(name = "user_id")) // Переопределили имя колонки родителя
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private Integer age;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public User() {}

    private User(String name, String lastName, Integer age, String email) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    public String getName() {return name;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public Integer getAge() {return age;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setName(String name) {this.name = name;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setAge(Integer age) {this.age = age;}

    @Override
    public String toString() {
        return String.format("ID: %s | Имя: %s %s | Email: %s | Возраст: %d | Создан: %s",
                getId(), name, lastName, (email != null ? email : "не указан"), age, createdAt);
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String name;
        private String lastName;
        private String email;
        private Integer age;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public User build() {
            if (name == null || name.isBlank()) {throw  new IllegalStateException("Пустое имя");}
            if (lastName == null || lastName.isBlank()) {throw  new IllegalStateException("Пустая фамилия");}
            if (age == null) {throw  new IllegalStateException("Пустой возраст");}
            return new User(name, lastName, age, email);
        }
    }
}
