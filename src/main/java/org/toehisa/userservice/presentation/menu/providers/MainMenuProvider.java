package org.toehisa.userservice.presentation.menu.providers;

import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.entities.*;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;
import org.toehisa.userservice.service.UserService;

import java.util.Map;

import static org.toehisa.userservice.presentation.menu.routes.MenuRoutes.*;

public class MainMenuProvider extends MenuProvider {

    private final ConsoleInputReader inputReader;
    private final ConsoleOutputWriter outputWriter;
    private final UserService userService;

    public MainMenuProvider(ConsoleInputReader inputReader,
                            ConsoleOutputWriter outputWriter,
                            UserService userService) {
        super();
        this.inputReader = inputReader;
        this.outputWriter = outputWriter;
        this.userService = userService;
    }

    @Override
    public Map<MenuRoutes, Menu> provideMenus() {
        return Map.of(
                MAIN, new MainMenu(MAIN, inputReader, outputWriter),
                CREATE, new CreateMenu(CREATE, inputReader, outputWriter, userService),
                SEARCH, new SearchMenu(SEARCH, inputReader, outputWriter),
                SEARCH_ALL, new ShowAllUsersMenu(SEARCH_ALL, outputWriter, userService),
                SEARCH_BY_ID, new ShowUserByIdMenu(SEARCH_BY_ID, inputReader, outputWriter, userService),
                UPDATE, new UpdateMenu(UPDATE, inputReader, outputWriter, userService),
                DELETE, new DeleteMenu(DELETE, inputReader, outputWriter, userService),
                EXIT, new ExitMenu(outputWriter)
        );
    }
}