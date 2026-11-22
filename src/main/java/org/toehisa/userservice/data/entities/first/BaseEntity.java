    package org.toehisa.userservice.data.entities.first;

    import jakarta.persistence.*;

    import java.util.UUID;

    //Способ наследования №1 - Внедрение суперкласса

    /**
     * Минусы
     * - Родителя нельзя использовать в связях @ManyToOne, @OneToOne, @OneToMany
     * - Нельзя сделать SELECT, к примеру session.createQuery("FROM BaseEntity")
    */
    @MappedSuperclass//Аннотация маркер, говорит о том что наследуемая сущность будет хранить поля родителя
    public abstract class BaseEntity {
        //По сути это переключатель стратегии работы с кешем и бд в методе session.persist()
        //@GeneratedValue(strategy = GenerationType.IDENTITY) // Запрос id у бд, сохраняет запись в пуле транзакции, если транзакция булщит то откат и дыра в нумерации бд
        //@GeneratedValue(strategy = GenerationType.SEQUENCE) //Запрос пачки id у объекта счетчика в джаве, лучший выбор при создании доменов пачкой.
        @Id
        @GeneratedValue(strategy = GenerationType.UUID) //Запрос id у JVM, требует больше памяти и ресурсов цп, но при этом самый защищенный вариант.
        private UUID id;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
    }
