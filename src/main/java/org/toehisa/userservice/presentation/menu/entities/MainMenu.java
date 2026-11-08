package org.toehisa.userservice.presentation.menu.entities;


import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.*;

public class MainMenu extends Menu {

    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;

    public MainMenu(MenuRoutes routeID, ConsoleInputReader inputReader, ConsoleOutputWriter outputWriter) {
        super(routeID);
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("1. Создать нового пользователя");
        outputWriter.print("2. Найти пользователя по ID");
        outputWriter.print("3. Изменить данные пользователя (по ID)");
        outputWriter.print("4. Удалить пользователя (по ID)");
        outputWriter.print("5. Выход из программы");
    }


    @Override
    public MenuRoutes execute() {
        return inputReader.readUntilValid(
                "Введите число: ",
                (userInput) -> MenuRoutes.fromStr(
                        userInput,
                        CREATE,
                        SEARCH,
                        UPDATE,
                        DELETE,
                        EXIT)
        );
    }
}