package org.toehisa.userservice;

import org.toehisa.userservice.presentation.input.ConsoleInputReader;
import org.toehisa.userservice.presentation.menu.providers.MainMenuProvider;
import org.toehisa.userservice.presentation.menu.providers.MenuProvider;
import org.toehisa.userservice.presentation.menu.selectors.MainMenuSelector;
import org.toehisa.userservice.presentation.menu.selectors.MenuSelector;
import org.toehisa.userservice.presentation.output.ConsoleOutputWriter;

import java.util.Scanner;
public class Launcher {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            MenuProvider menuProvider = buildMenuProvider(scanner);
            MenuSelector menuSelector = new MainMenuSelector(menuProvider);
            menuSelector.run();
        }
    }

    private static MenuProvider buildMenuProvider(Scanner scanner) {
        ConsoleInputReader inputReader = new ConsoleInputReader(scanner);
        ConsoleOutputWriter outputWriter = new ConsoleOutputWriter();
        return new MainMenuProvider(inputReader, outputWriter);
    }
}