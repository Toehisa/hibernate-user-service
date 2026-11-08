package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;
import org.toehisa.userservice.service.UserServiceImpl;

import java.util.List;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class ShowAllUsersMenu extends Menu {
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService = new UserServiceImpl();

    public ShowAllUsersMenu(MenuRoutes routeID, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("--- Список всех пользователей ---");
    }

    @Override
    public MenuRoutes execute() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            outputWriter.print("База данных пуста.");
        } else {
            users.forEach(user -> outputWriter.print(user.toString()));
        }

        return MAIN;
    }
}