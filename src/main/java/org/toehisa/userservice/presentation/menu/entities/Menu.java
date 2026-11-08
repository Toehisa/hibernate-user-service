package org.toehisa.userservice.presentation.menu.entities;

import org.toehisa.userservice.presentation.menu.routes.MenuRoutes;

abstract public class Menu {
    protected final MenuRoutes routeID;

    public Menu(MenuRoutes routeID) {
        this.routeID = routeID;
    }

    public abstract void show();
    public abstract MenuRoutes execute();
}