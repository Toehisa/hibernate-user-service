package org.toehisa.userservice.service;

import org.toehisa.userservice.data.dao.DataAccessObject;
import org.toehisa.userservice.data.dao.UserDataAccessObject;
import org.toehisa.userservice.data.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final DataAccessObject<User> userDao = new UserDataAccessObject();

    @Override
    public void createUser(User user) {
        validateUser(user);
        userDao.save(user);
    }

    @Override
    public User getUserById(UUID id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID не может быть null");
        }
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void updateUser(User user) {
        validateUser(user);
        userDao.update(user);
    }

    @Override
    public void deleteUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Пользователь для удаления не найден");
        }
        userDao.delete(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Фамилия не может быть пустой");
        }
        if (user.getAge() == null || user.getAge() < 12 || user.getAge() > 100) {
            throw new IllegalArgumentException("Возраст должен быть от 12 до 100 лет");
        }
    }
}
