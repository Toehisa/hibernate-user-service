package org.toehisa.userservice.service;

import org.toehisa.userservice.data.entities.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(User user);
    User getUserById(UUID id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(User user);
}
