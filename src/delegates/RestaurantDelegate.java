package delegates;

import model.*;

/**
 * This interface uses the delegation design pattern where instead of having
 * the CreateAndShowGUI class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the
 * controller class.
 *
 * CreateAndShowGUI calls the methods that we have listed below but
 * Restaurant is the actual class that will implement the methods.
 */

public interface RestaurantDelegate {
    Menus showMenus();
    Reservations showReservations();
    Branches showBranches();

    void insertBranch(Branch newBranch);
    void insertMenu(Menu newMenu);
    void insertReservation(Reservation newReservation);

    void deleteMenu(String tblName);
    void deleteReservation(int tblRId);
    void deleteBranch(int tblLocId);

    Menus showMenusFromAllBranches();
}
