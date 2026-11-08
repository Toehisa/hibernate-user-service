package org.toehisa.userservice.presentation.menu.providers;

import org.toehisa.userservice.presentation.menu.entities.Menu;
import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;

import java.util.Map;

abstract public class MenuProvider {
    protected int startIdx;

    public MenuProvider() {
        this.startIdx = 0;
    }
    public MenuProvider(int startIdx) {
        this.startIdx = startIdx;
    }

    public abstract Map<MenuRoutes, Menu> provideMenus ();
    public int getStartIdx() {
        return startIdx;
    }
}
