package controller;

import Delegate.RestaurantDelegate;
import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import model.Branch;
import model.Menu;
import model.Reservation;
import ui.CreateAndShowGUI;
import ui.LoginWindow;

public class Restaurant implements RestaurantDelegate, LoginWindowDelegate {
    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;

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
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        restaurant.logInWindow();
    }
    
    public void logInWindow() {
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
    }
    
    public void databaseSetup() {
        dbHandler.databaseSetup();
    }
    
    public void deleteBranch(int branchId) {
        
    }

    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);

        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            loginWindow.dispose();
            
            CreateAndShowGUI transaction = new CreateAndShowGUI();
            transaction.setUpDatabase(this);
            
        } else {
            loginWindow.handleLoginFailed();
            //dbHandler.deleteBranch(branchId);
        }
    }

    public void insertBranch(Branch model) {
            dbHandler.insertBranch(model);
    }
    
    public void showBranch() {
        
    }

    public void updateBranch(int branchId, String name) {

    }
    
    public void terminalTransactionsFinished() {
        dbHandler.close();
        dbHandler = null;

        System.exit(0);
    }
    
    public void insertMenu(Menu model) {
        dbHandler.insertMenu(model);
    }
    
    public void insertReservation(Reservation model) {
        dbHandler.insertReservation(model);
    }
}

