package org.toehisa.userservice.presentation.menu.entities;

import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class CreateMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService;

    public CreateMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter, UserService userService) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.userService = userService;
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

        UserRequestDto dto = new UserRequestDto(name, lastName, null, age);

        try {
            userService.createUser(dto);
            outputWriter.print("Пользователь успешно сохранен в базу данных!");
        } catch (ResponseStatusException e) {
            outputWriter.printError("Ошибка создания: " + e.getReason());
        }

        return MAIN;
    }
}