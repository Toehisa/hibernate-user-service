package org.toehisa.userservice.presentation.output;

public class ConsoleOutputWriter {

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
