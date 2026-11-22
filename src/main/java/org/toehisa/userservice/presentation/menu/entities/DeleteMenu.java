package org.toehisa.userservice.presentation.menu.entities;

import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;

import java.util.UUID;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.MAIN;

public class DeleteMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService;

    public DeleteMenu(MenuRoutes routeID,
                      ConsoleInputReader inputReader,
                      ConsoleOutputWriter outputWriter,
                      UserService userService) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.userService = userService;
    }

    @Override
    public void show() {
        outputWriter.print("--- Удаление пользователя ---");
    }

    @Override
    public MenuRoutes execute() {
        UUID id = inputReader.readUUID("Введите ID пользователя для удаления: ");

        try {
            org.toehisa.userservice.data.dto.UserResponseDto user = userService.getUserById(id);
            outputWriter.print("Удаление пользователя: " + user);
            userService.deleteUser(id);
            outputWriter.print("Пользователь успешно удален!");
        } catch (ResponseStatusException e) {
            outputWriter.printError("Ошибка удаления: " + e.getReason());
        }

        return MAIN;
    }
}