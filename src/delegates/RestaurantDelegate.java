package delegates;

import model.*;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class (in this case Bank).
 *
 * TerminalTransactions calls the methods that we have listed below but
 * Bank is the actual class that will implement the methods.
 */

public interface RestaurantDelegate {
    public Branches showBranches();
    public Menus showMenus();

    void insertBranch(Branch newBranch);

    void insertMenu(Menu newMenu);

    void insertReservation(Reservation newReservation);
}
