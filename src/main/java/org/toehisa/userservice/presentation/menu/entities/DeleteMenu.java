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

public class DeleteMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService = new UserServiceImpl();

    public DeleteMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("--- Удаление пользователя ---");
    }

    @Override
    public MenuRoutes execute() {
        UUID id = inputReader.readUUID("Введите ID пользователя для удаления: ");
        User user = userService.getUserById(id);

        if (Objects.isNull(user)) {
            outputWriter.print("Пользователь с таким ID не найден.");
            return MAIN;
        }

        outputWriter.print("Удаление пользователя: " + user);

        try {
            userService.deleteUser(user);
            outputWriter.print("Пользователь успешно удален!");
        } catch (IllegalArgumentException e) {
            outputWriter.printError("Ошибка удаления: " + e.getMessage());
        }

        return MAIN;
    }
}