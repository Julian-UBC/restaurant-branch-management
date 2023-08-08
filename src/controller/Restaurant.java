package controller;

import Delegate.RestaurantDelegate;
import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import model.Branch;
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

    @Override
    public void databaseSetup() {

    }

    @Override
    public void deleteBranch(int branchId) {

    }

    @Override
    public void insertBranch(Branch model) {

    }

    @Override
    public void showBranch() {

    }

    @Override
    public void updateBranch(int branchId, String name) {

    }

    @Override
    public void terminalTransactionsFinished() {

    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        new Restaurant();
    }
}

