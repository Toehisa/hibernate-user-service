package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;

public class ExitMenu extends Menu {

    private final ConsoleOutputWriter outputWriter;

    public ExitMenu(ConsoleOutputWriter outputWriter) {
        super(MenuRoutes.EXIT);
        this.outputWriter = outputWriter;
    }

    @Override
    public void show() {
        outputWriter.print("Завершение работы программы. До свидания!");
    }

    @Override
    public MenuRoutes execute() {
        return MenuRoutes.EXIT;
    }
}