package database;

import model.*;
import util.PrintablePreparedStatement;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;

    public DatabaseConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            return false;
        }
    }

    private void rollbackConnection() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public Menus getMenusInfo() {
        Menus menus = new Menus();

        try {
            String query = "SELECT * FROM Menu";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Menu menu = new Menu(rs.getString("name"),
                        rs.getFloat("cost"),
                        rs.getString("category"));
                menus.addMenu(menu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return menus;
    }

    public Reservations getReservationsInfo() {
        Reservations reservations = new Reservations();

        try {
            String query = "SELECT * FROM Reservations";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            while(rs.next()) {
                Reservation reservation = new Reservation(rs.getInt("rID"),
                                                            rs.getInt("cID"),
                                                            rs.getInt("locID"),
                                                            rs.getInt("wID"),
                                                            rs.getDate("rDate").toLocalDate(),
                                                            timeFormat.format(rs.getDate("rTime")),
                                                            rs.getInt("numOfPeople"),
                                                            rs.getString("reservationName"));

                reservations.addReservation(reservation);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return reservations;
    }

    public Branches getBranchesInfo() {
        Branches branches = new Branches();

        try {
            String query = "SELECT * FROM Branches";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Branch branch = new Branch(rs.getInt("locID"),
                                            rs.getString("streetAddress"),
                                            rs.getString("city"),
                                            rs.getString("province"));

                branches.addBranches(branch);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return branches;
    }

    public void insertBranch(Branch model) {
        try {
            String query = "INSERT INTO branches VALUES (?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getLocId());
            ps.setString(2, model.getStreetAddress());
            ps.setString(3, model.getCity());
            ps.setString(4, model.getProvince());
            
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertMenu(Menu model) {
        try {
            String query = "INSERT INTO menu VALUES (?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, model.getName());
            ps.setFloat(2, model.getCost());
            ps.setString(3, model.getCategory());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insertReservation(Reservation model) {
        try {
            String query = "INSERT INTO reservations VALUES (?,?,?,?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getRId());
            ps.setInt(2, model.getCId());
            ps.setInt(3, model.getLocId());
            ps.setInt(4, model.getWId());
            ps.setDate(5, Date.valueOf(model.getDate()));
            ps.setTime(6, Time.valueOf(model.getTime()));
            ps.setInt(7,model.getNumOfPeople());
            ps.setString(8,model.getReservationName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteMenu(String tblName) {
        try {
            String query = "DELETE FROM Menu WHERE name = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, tblName);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Menu " + tblName + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteReservation(int tblRId) {
        try {
            String query = "DELETE FROM Reservations WHERE rID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tblRId);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Reservation " + tblRId + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteBranch(int tblLocId) {
        try {
            String query = "DELETE FROM Branches WHERE locID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tblLocId);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Branch " + tblLocId + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void updateMenu(String ogName, String name, float cost, String category) {
        try{
            String query = "UPDATE Menu SET name = ?, cost = ?, category = ? WHERE name = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1,name);
            ps.setFloat(2, cost);
            ps.setString(3, category);
            ps.setString(4, ogName);

            ps.executeUpdate();
            connection.commit();

            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    public void updateReservation(int ogrID, int rID, int cID, int locID, int wID, LocalDate rDate, LocalTime rTime, int numOfPeople, String reservationName) {
        try {
            String query = "UPDATE Reservations SET rID = ?, cID = ?, locID = ?, wID = ?, rDate =?, rTime = ?, numOfPeople =?, reservationName =? WHERE rID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, rID);
            ps.setInt(2, cID);
            ps.setInt(3, locID);
            ps.setInt(4, wID);
            ps.setDate(5, Date.valueOf(rDate));
            ps.setTime(6, Time.valueOf(rTime));
            ps.setInt(7, numOfPeople);
            ps.setString(8, reservationName);
            ps.setInt(9, ogrID);

            ps.executeUpdate();
            connection.commit();

            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateBranch(int oglocID, int locID, String streetAddress, String city, String province) {
        try{
            String query = "UPDATE Branches SET locID = ?, streetAddress = ?, city = ?, province =? WHERE locID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1,locID);
            ps.setString(2, streetAddress);
            ps.setString(3, city);
            ps.setString(4, province);
            ps.setInt(5, oglocID);

            ps.executeUpdate();
            connection.commit();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    
    public JoinedBranchReservation joinBranchReservation(LocalDate currentDate, LocalDate lastDate){

        JoinedBranchReservation JoinedBranchReservation = new JoinedBranchReservation();
        try{
            String query = "SELECT* FROM Branches b, Reservations r WHERE r.locID = b.locID AND rDATE >= ? AND rDate <= ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setDate(1,Date.valueOf(currentDate));
            ps.setDate(2,Date.valueOf(lastDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Branch branch = new Branch(rs.getInt("locID"), rs.getString("streetAddress"), rs.getString("city"), rs.getString("Province"));
                Reservation reservation = new Reservation(rs.getInt("rID"), rs.getInt("cID"), rs.getInt("locID"), rs.getInt("wID"), rs.getDate("rDate").toLocalDate(), rs.getString("rTime"), rs.getInt("numOfPeople"), rs.getString("reservationName"));
                JoinedBranchReservation.addJoinedBranchReservation(branch,reservation);
            }
            connection.commit();
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return JoinedBranchReservation;
    }
   
    public List<List<String>> projection(List<String> attributes, List<String> columnsDomain, String tableSelected) {
        List<List<String>> tuples = new ArrayList<>();
        String selectColumns = attributes.get(0);

        for (int i = 1; i < attributes.size(); i++) {
            selectColumns = selectColumns + ", " + attributes.get(i);
        }

        try {
            String query = "SELECT DISTINCT " + selectColumns + " " +
                    "FROM " + tableSelected + " s";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                List<String> tuple = new ArrayList<>();
                for (int i = 0; i < columnsDomain.size(); i++) {
                    String domain = columnsDomain.get(i);
                    String data;

                    switch (domain) {
                        case "float" -> {
                            data = String.valueOf(rs.getFloat(attributes.get(i)));
                        }
                        case "int" -> {
                            data = String.valueOf(rs.getInt(attributes.get(i)));
                        }
                        case "date" -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            data = dateFormat.format(rs.getDate(attributes.get(i)));
                        }
                        case "time" -> {
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            data = timeFormat.format(rs.getDate(attributes.get(i)));
                        }
                        default -> {
                            data = rs.getString(attributes.get(i));
                        }
                    }

                    tuple.add(data);
                }

                tuples.add(tuple);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return tuples;
    }
    
    public MenuSorted showNestedAggregation() {
        MenuSorted MenuSorted = new MenuSorted();
        try {
            String query = "SELECT category, AVG(cost) " +
                    "FROM Menu m " +
                    "GROUP BY category " +
                    "HAVING AVG(cost) <=" + "ALL(" +
                        "SELECT AVG(m2.cost)" +
                        "FROM Menu m2 " +
                        "GROUP BY category)";
            System.out.println(query);
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AvgCostHaving menu = new AvgCostHaving(rs.getString("CATEGORY"), rs.getFloat("AVG(COST)"));
                MenuSorted.addSortedMenu(menu);
            }

            rs.close();
            ps.close();
        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return MenuSorted;
    }
    
    public MenuSorted showGroupBy() {
        MenuSorted MenuSorted = new MenuSorted();
        try {
            String query = "SELECT category, AVG(cost) " +
                           "FROM Menu m " +
                           "GROUP BY category"; 
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AvgCostHaving menu = new AvgCostHaving(rs.getString("CATEGORY"), rs.getFloat("AVG(COST)"));
                MenuSorted.addSortedMenu(menu);
            }

            rs.close();
            ps.close();
        } catch(SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return MenuSorted;
    }

    public Menus showDivision() {
        Menus menus = new Menus();

        try {
            String query = "SELECT name " +
                            "FROM Menu m " +
                            "WHERE NOT EXISTS (" +
                                                "(SELECT locID FROM Branches b) " +
                                                "MINUS " +
                                                "(SELECT locID " +
                                                "FROM MenuServed ms " +
                                                "WHERE m.name = ms.menuName))";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Menu menu = new Menu(rs.getString("name"));
                menus.addMenu(menu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
        return menus;
    }

    public MenusAvgCost showAvgCostMenu() {
        MenusAvgCost menus = new MenusAvgCost();

        try {
            String query = "SELECT category, AVG(cost) as avgCost " +
                    "FROM Menu m " +
                    "GROUP BY category " +
                    "HAVING COUNT(category) > 1";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                AvgCostHaving menu = new AvgCostHaving(rs.getString("category"), rs.getFloat("avgCost"));
                menus.addAvgCostHaving(menu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return menus;
    }
    public List<List<String>> filter(List<String> columnsSelected, List<String> columnsDomain, List<String> filterConditions, String tableSelected) {
        List<List<String>> filteredMenus = new ArrayList<>();
        String selectColumns = columnsSelected.get(0);

        for (int i = 1; i < columnsSelected.size(); i++) {
            selectColumns = selectColumns + ", " + columnsSelected.get(i);
        }

        String conditions;
        if (filterConditions.size() == 0) {
            conditions = "";
        } else {
            conditions = filterConditions.get(0);
            if (conditions.contains("rTime =")) {
                String time = conditions.replace("rTime = '", "");
                time = time.replace("'","");
                conditions = "rTime = to_date('1970-01-01 " + time + "' ,'yyyy-mm-dd HH24:MI:SS')";
            }
            for (int i = 1; i < filterConditions.size(); i++) {
                String temp = filterConditions.get(i);
                if (temp.contains("rTime =")) {
                    String time = temp.replace("rTime = '", "");
                    time = time.replace("'","");
                    conditions = conditions + " AND " + "rTime = " + "to_date('1970-01-01" + time + "' ,'yyyy-mm-dd HH24:MI:SS')";
                } else {
                    conditions = conditions + " AND " + filterConditions.get(i);
                }
            }
        }

        try {
            String query;
            if (filterConditions.size() == 0) {
                query = "SELECT " + selectColumns + " " +
                        "FROM " + tableSelected + " s";
            } else {
                query = "SELECT " + selectColumns + " " +
                        "FROM " + tableSelected + " s " +
                        "WHERE " + conditions;
            }
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                List<String> filteredMenu = new ArrayList<>();
                for (int i = 0; i < columnsDomain.size(); i++) {
                    String domain = columnsDomain.get(i);
                    String data;

                    switch (domain) {
                        case "float" -> {
                            data = String.valueOf(rs.getFloat(columnsSelected.get(i)));
                        }
                        case "int" -> {
                            data = String.valueOf(rs.getInt(columnsSelected.get(i)));
                        }
                        case "date" -> {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            data = dateFormat.format(rs.getDate(columnsSelected.get(i)));
                        }
                        case "time" -> {
                            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            data = timeFormat.format(rs.getDate(columnsSelected.get(i)));
                        }
                        default -> {
                            data = rs.getString(columnsSelected.get(i));
                        }
                    }

                    filteredMenu.add(data);
                }

                filteredMenus.add(filteredMenu);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return filteredMenus;
    }
}
