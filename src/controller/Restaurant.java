package controller;

import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import delegates.RestaurantDelegate;
import model.Branch;
import model.Branches;
import model.Menu;
import model.Menus;
import model.Reservation;
import ui.CreateAndShowGUI;
import ui.LoginWindow;

public class Restaurant implements RestaurantDelegate, LoginWindowDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

    public Restaurant() {
        dbHandler = new DatabaseConnectionHandler();
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }

    /**
     * LoginWindowDelegate Implementation
     * <p>
     * connects to Oracle database with supplied username and password
     */
    @Override
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            new CreateAndShowGUI(this);
        } else {
            loginWindow.handleLoginFailed();

            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    public void insertBranch(Branch model) {
        dbHandler.insertBranch(model);
    }

    @Override
    public void insertMenu(Menu model) {
        dbHandler.insertMenu(model);
    }

    @Override
    public void insertReservation(Reservation model) {
        dbHandler.insertReservation(model);
    }
    
    @Override
    public Branches showBranches() {
        return null;
    }

    @Override
    public Menus showMenus() {
        return dbHandler.getMenusInfo();
    }


    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        new Restaurant();
    }

    
}

