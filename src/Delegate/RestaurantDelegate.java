package Delegate;

import model.Branch;
import model.Menu;
import model.Reservation;

public interface RestaurantDelegate {
    public void databaseSetup();

    public void deleteBranch(int branchId);
    public void insertBranch(Branch model);
    public void insertMenu(Menu model);
    public void insertReservation(Reservation model);
    public void showBranch();
    public void updateBranch(int branchId, String name);
    public void terminalTransactionsFinished();
}

