package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;
import org.toehisa.userservice.service.UserServiceImpl;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class CreateMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService = new UserServiceImpl();

    public CreateMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("--- Создание пользователя ---");
    }

    @Override
    public MenuRoutes execute() {
        String name = inputReader.readLetters("Введите имя: ");
        String lastName = inputReader.readLetters("Введите фамилию: ");
        Integer age = inputReader.readIntInRange("Введите возраст: ", 12, 100);

        User user = User.builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .build();

        userService.createUser(user);
        outputWriter.print("Пользователь успешно сохранен в базу данных!");

        return MAIN;
    }
}