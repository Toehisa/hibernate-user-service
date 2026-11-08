package org.toehisa.userservice.presentation.menu.selectors;


import org.toehisa.userservice.presentation.menu.entities.Menu;
import org.toehisa.userservice.presentation.menu.providers.MenuProvider;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;

public class MainMenuSelector extends MenuSelector {
    public MainMenuSelector(MenuProvider provider) {
        super(provider);
        cursor = MenuRoutes.MAIN;
    }

    @Override
    protected void showMenu() {
        currentMenu().show();
    }

    @Override
    protected void switchMenu(MenuRoutes route) {
        cursor = route;
    }

    @Override
    protected Menu currentMenu() {
        return menus.get(cursor);
    }


    @Override
    public void run() {
        do {
            showMenu();
            var route = currentMenu().execute();
            switchMenu(route);
        } while (!cursorIsExit());

        showMenu();
    }

    private boolean cursorIsExit() {
        return cursor == MenuRoutes.EXIT;
    }
}
