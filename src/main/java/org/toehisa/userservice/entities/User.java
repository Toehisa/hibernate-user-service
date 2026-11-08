package org.toehisa.userservice.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {
    //По сути это переключатель стратегии работы с кешем и бд в методе session.persist()
    //@GeneratedValue(strategy = GenerationType.IDENTITY) // Запрос id у бд, сохраняет запись в пуле транзакции, если транзакция булщит то откат и дыра в нумерации бд
    //@GeneratedValue(strategy = GenerationType.SEQUENCE) //Запрос пачки id у объекта счетчика в джаве, лучший выбор при создании доменов пачкой.
    @GeneratedValue(strategy = GenerationType.UUID) //Запрос id у JVM, требует больше памяти и ресурсов цп, но при этом самый защищенный вариант.
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "age")
    private Integer age;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected User() {}

    //Геттеры нормальных людей
    public Long id() {return id;}
    public String name() {return name;}
    public String email() {return email;}
    public Integer age() {return age;}
    public LocalDateTime createdAt() {return createdAt;}

    //Геттеры палеозоя для всяких библиотек
    public Long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public Integer getAge() {return age;}
    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setAge(Integer age) {this.age = age;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}
