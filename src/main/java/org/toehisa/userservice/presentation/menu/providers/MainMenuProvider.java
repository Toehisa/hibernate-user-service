package org.toehisa.userservice.presentation.menu.providers;

import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.entities.*;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;

import java.util.Map;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.*;

public class MainMenuProvider extends MenuProvider {

    private final ConsoleInputReader inputReader;
    ConsoleOutputWriter outputWriter;

    public MainMenuProvider(ConsoleInputReader inputReader,ConsoleOutputWriter outputWriter) {
        super();
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
    }
    @Override
    public Map<MenuRoutes, Menu> provideMenus() {
        return Map.of(
                MAIN, new MainMenu(MAIN, inputReader, outputWriter),
                CREATE, new CreateMenu(CREATE, inputReader, outputWriter),
                SEARCH, new SearchMenu(SEARCH, inputReader, outputWriter),
                SEARCH_ALL, new ShowAllUsersMenu(SEARCH_ALL, outputWriter),
                SEARCH_BY_ID, new ShowUserByIdMenu(SEARCH_BY_ID, inputReader, outputWriter),
                UPDATE, new UpdateMenu(UPDATE, inputReader, outputWriter),
                DELETE, new DeleteMenu(DELETE, inputReader, outputWriter),
                EXIT, new ExitMenu(outputWriter)
        );
    }
}
