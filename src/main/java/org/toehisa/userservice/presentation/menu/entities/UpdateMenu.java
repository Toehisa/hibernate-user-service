package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;
import org.toehisa.userservice.service.UserServiceImpl;

import java.util.UUID;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class UpdateMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService = new UserServiceImpl();

    public UpdateMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("--- Изменение данных пользователя ---");
    }

    @Override
    public MenuRoutes execute() {
        UUID id = inputReader.readUUID("Введите ID пользователя для изменения: ");
        User user = userService.getUserById(id);

        if (user == null) {
            outputWriter.print("Пользователь с таким ID не найден.");
            return MAIN;
        }

        outputWriter.print("Текущие данные: " + user);

        String newName = inputReader.readLetters("Введите новое имя: ");
        String newLastName = inputReader.readLetters("Введите новую фамилию: ");
        Integer newAge = inputReader.readIntInRange("Введите новый возраст: ", 12, 100);

        user.setName(newName);
        user.setLastName(newLastName);
        user.setAge(newAge);

        try {
            userService.updateUser(user);
            outputWriter.print("Данные успешно обновлены!");
        } catch (IllegalArgumentException e) {
            outputWriter.printError("Ошибка обновления: " + e.getMessage());
        }

        return MAIN;
    }
}