package delegates;

import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    void updateMenu(String ogName, String name, float cost, String category);
    void updateReservation(int ogrID, int rID, int cID, int locID, int wID, LocalDate rDate, LocalTime rTime, int numOfPeople, String reservationName);
    void updateBranch(int oglocID, int locID, String streetAddress, String city, String province);
    JoinedBranchReservation joinBranchReservation(LocalDate currentDate, LocalDate lastDate);

    MenuSorted showGroupBy();

    MenuSorted showNestedAggregation();

    Menus showMenusFromAllBranches();

    MenusAvgCost showAvgCostMenuHaving();

    List<List<String>> filter(List<String> columnsSelected, List<String> columnsDomain, String tableSelected);

//    void filterMenus(List<String> columnsSelected);
}
