package org.toehisa.userservice.data.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    //По сути это переключатель стратегии работы с кешем и бд в методе session.persist()
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // Запрос id у бд, сохраняет запись в пуле транзакции, если транзакция булщит то откат и дыра в нумерации бд
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) //Запрос пачки id у объекта счетчика в джаве, лучший выбор при создании доменов пачкой.
    @GeneratedValue(strategy = GenerationType.UUID) //Запрос id у JVM, требует больше памяти и ресурсов цп, но при этом самый защищенный вариант.
    @Id
    @Column(name = "id")
    private UUID id;
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

    protected User() {}

    private User(String name, String lastName, Integer age, String email) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    //Геттеры нормальных людей
    public UUID id() {return id;}
    public String name() {return name;}
    public String lastName() {return lastName;}
    public String email() {return email;}
    public Integer age() {return age;}
    public LocalDateTime createdAt() {return createdAt;}

    //Геттеры палеозоя для всяких библиотек
    public UUID getId() {return id;}
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
                id, name, lastName, (email != null ? email : "не указан"), age, createdAt);
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
