package controller;

import Delegate.RestaurantDelegate;
import database.DatabaseConnectionHandler;
import model.Branch;

public class Restaurant implements RestaurantDelegate {
    private DatabaseConnectionHandler dbHandler = null;

    public Restaurant() {
        dbHandler = new DatabaseConnectionHandler();
    }

//    /**
//     * LoginWindowDelegate Implementation
//     *
//     * connects to Oracle database with supplied username and password
//     */
//    @Override
//    public void login(String username, String password) {
//        boolean didConnect = dbHandler.login(username, password);
//
//        if (didConnect) {
//            // Once connected, remove login window and start text transaction flow
//            loginWindow.dispose();
//
//            CreateAndShowGUI transaction = new CreateAndShowGUI(this);
//        } else {
//            loginWindow.handleLoginFailed();
//
//            if (loginWindow.hasReachedMaxLoginAttempts()) {
//                loginWindow.dispose();
//                System.out.println("You have exceeded your number of allowed attempts");
//                System.exit(-1);
//            }
//        }
//    }
//
//    @Override
//    public void showBranch() {
//
//    }
//
    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        new Restaurant();
    }
    
    public void databaseSetup() {
        dbHandler.databaseSetup();
    }

    @Override
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();

            new CreateAndShowGUI(this);
        } else {
            loginWindow.handleLoginFailed();

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
    
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }
}

