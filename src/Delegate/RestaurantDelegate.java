package Delegate;

import model.Branch;

// code is taken from 
public interface RestaurantDelegate {
    public void databaseSetup();

    public void deleteBranch(int branchId);
    public void insertBranch(Branch model);
    public void showBranch();
    public void updateBranch(int branchId, String name);

    public void terminalTransactionsFinished();
}

