package org.toehisa.userservice.presentation.menu.entities;

import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.data.dto.UserResponseDto;
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
    private final UserService userService;

    public UpdateMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter, UserService userService) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.userService = userService;
    }

    @Override
    public void show() {
        outputWriter.print("--- Изменение данных пользователя ---");
    }

    @Override
    public MenuRoutes execute() {
        UUID id = inputReader.readUUID("Введите ID пользователя для изменения: ");

        try {
            UserResponseDto user = userService.getUserById(id);
            outputWriter.print("Текущие данные: " + user);

            String newName = inputReader.readLetters("Введите новое имя: ");
            String newLastName = inputReader.readLetters("Введите новую фамилию: ");
            Integer newAge = inputReader.readIntInRange("Введите новый возраст: ", 18, 100);

            UserRequestDto updatedDto = new UserRequestDto(newName, newLastName, user.email(), newAge);
            userService.updateUser(id, updatedDto);
            outputWriter.print("Данные успешно обновлены!");
        } catch (ResponseStatusException e) {
            outputWriter.printError("Ошибка обновления: " + e.getReason());
        }

        return MAIN;
    }
}