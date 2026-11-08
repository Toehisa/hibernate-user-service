package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;

public class SearchMenu extends Menu {
    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter; // Добавили Writer

    public SearchMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("1. Посмотреть список всех пользователей");
        outputWriter.print("2. Поиск пользователя по ID");
        outputWriter.print("3. Вернуться на главную");
    }

    @Override
    public MenuRoutes execute() {
        int num = inputReader.readIntInRange("Введите число: ", 1, 3);
        return switch (num) {
            case 1 -> MenuRoutes.SEARCH_ALL;
            case 2 -> MenuRoutes.SEARCH_BY_ID;
            case 3 -> MenuRoutes.MAIN;
            default -> throw new IllegalStateException("Сломалась валидация в readIntInRange");
        };
    }
}