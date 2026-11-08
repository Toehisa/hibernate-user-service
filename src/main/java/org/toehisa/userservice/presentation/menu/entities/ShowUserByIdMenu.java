package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;
import org.toehisa.userservice.service.UserServiceImpl;

import java.util.Objects;
import java.util.UUID;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class ShowUserByIdMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService = new UserServiceImpl();

    public ShowUserByIdMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("--- Поиск пользователя по ID ---");
    }

    @Override
    public MenuRoutes execute() {
        UUID id = inputReader.readUUID("Введите ID пользователя: ");
        User user = userService.getUserById(id);

        showMessageOnUserExist(user);

        return MAIN;
    }

    private void showMessageOnUserExist(User user) {
        if (Objects.nonNull(user)) {
            outputWriter.print("Пользователь найден:\n" + user);
        } else {
            outputWriter.print("Пользователь с таким ID не найден.");
        }
    }
}