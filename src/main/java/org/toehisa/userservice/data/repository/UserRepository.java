package org.toehisa.userservice.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.toehisa.userservice.data.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> { }
