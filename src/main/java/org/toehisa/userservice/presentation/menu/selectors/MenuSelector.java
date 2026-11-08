package org.toehisa.userservice.presentation.menu.selectors;

import org.toehisa.userservice.presentation.menu.entities.Menu;
import org.toehisa.userservice.presentation.menu.providers.MenuProvider;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;

import java.util.HashMap;
import java.util.Map;

abstract public class MenuSelector {
    final Map<MenuRoutes, Menu> menus;
    MenuRoutes cursor;

    MenuSelector() {
        menus = new HashMap<>();
    }
    MenuSelector(MenuProvider provider) {
        menus = provider.provideMenus();
    }

    abstract protected void showMenu();
    abstract protected void switchMenu(MenuRoutes route);
    abstract protected Menu currentMenu();
    abstract public void run();
}
