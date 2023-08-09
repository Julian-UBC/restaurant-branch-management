package controller;

import database.DatabaseConnectionHandler;
import delegates.LoginWindowDelegate;
import delegates.RestaurantDelegate;
import model.*;
import ui.CreateAndShowGUI;
import ui.LoginWindow;

import java.time.LocalDate;
import java.time.LocalTime;

public class Restaurant implements RestaurantDelegate, LoginWindowDelegate {
    private DatabaseConnectionHandler dbHandler;
    private LoginWindow loginWindow;

    public Restaurant() {
        dbHandler = new DatabaseConnectionHandler();
        loginWindow = new LoginWindow();
        loginWindow.showFrame(this);
//        new CreateAndShowGUI(this);
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
    public Menus showMenus() {
        return dbHandler.getMenusInfo();
    }

    @Override
    public Reservations showReservations() {
        return dbHandler.getReservationsInfo();
    }

    @Override
    public Branches showBranches() {
        return dbHandler.getBranchesInfo();
    }

    @Override
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
    public void deleteMenu(String tblName) {
        dbHandler.deleteMenu(tblName);
    }

    @Override
    public void deleteReservation(int tblRId) {
        dbHandler.deleteReservation(tblRId);
    }

    @Override
    public void deleteBranch(int tblLocId) {
        dbHandler.deleteBranch(tblLocId);
    }

    @Override
    public void updateMenu(String ogName, String name, float cost, String category) {
        dbHandler.updateMenu(ogName,name,cost,category);
    }

    @Override
    public void updateReservation(int ogrID, int rID, int cID, int locID, int wID, LocalDate rDate, LocalTime rTime, int numOfPeople, String reservationName) {
        dbHandler.updateReservation(ogrID, rID, cID, locID, wID, rDate, rTime, numOfPeople, reservationName);
    }

    @Override
    public void updateBranch(int oglocID, int locID, String streetAddress, String city, String province) {
        dbHandler.updateBranch(oglocID, locID, streetAddress, city, province);
    }

    @Override
    public MenuSorted showGroupBy() {
        return dbHandler.showGroupBy();
    }

    @Override
    public Menus showMenusFromAllBranches() {
        return dbHandler.showDivision();
    }
    /*
    @Override
    public MenusAvgCost showAvgCostMenuHaving() {
        return dbHandler.showAvgCostMenu();
    }
    
     */

    /**
     * Main method called at launch time
     */
    public static void main(String[] args) {
        new Restaurant();
    }

    
}

