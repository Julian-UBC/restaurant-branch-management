package database;

import model.Branch;
import model.Menu;
import model.Reservation;
import util.PrintablePreparedStatement;

import java.sql.*;
import java.util.ArrayList;

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

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
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

//    public void deleteBranch(int branchId) {
////        try {
////            String query = "DELETE FROM branch WHERE branch_id = ?";
////            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
////            ps.setInt(1, branchId);
////
////            int rowCount = ps.executeUpdate();
////            if (rowCount == 0) {
////                System.out.println(WARNING_TAG + " Branch " + branchId + " does not exist!");
////            }
////
////            connection.commit();
////
////            ps.close();
////        } catch (SQLException e) {
////            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
////            rollbackConnection();
////        }
//    }

    public void insertBranch(Branch model) {
        try {
            String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
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
            String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
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
            String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, model.getrId());
            ps.setInt(2, model.getcId());
            ps.setInt(3, model.getLocId());
            ps.setInt(4, model.getwId());
            ps.setDate(5, Date.valueOf(model.getDate()));
            ps.setTime(6,Time.valueOf(model.getTime()));
            ps.setInt(7, model.getNumOfPeople());
            ps.setString(8, model.getReservationName());
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
    
    public Branch[] getBranchInfo() {
        ArrayList<Branch> result = new ArrayList<Branch>();

        try {
            String query = "SELECT * FROM branch";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Branch model = new Branch(rs.getInt("branch_locId"),
                        rs.getString("branch_streetAddress"),
                        rs.getString("branch_city"),
                        rs.getString("branch_province"));
                result.add(model);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new Branch[result.size()]);
    }

    public void updateBranch(int id, String name) {
//        try {
//            String query = "UPDATE branch SET branch_name = ? WHERE branch_id = ?";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.setString(1, name);
//            ps.setInt(2, id);
//
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0) {
//                System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
//            }
//
//            connection.commit();
//
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
//        }
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }


    public void databaseSetup() {
//        dropBranchTableIfExists();
//
//        try {
//            String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//
//        BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
//        insertBranch(branch1);
//
//        BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
//        insertBranch(branch2);
    }
    
    private void dropBranchTableIfExists() {
        try {
            String query = "select table_name from user_tables";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                if(rs.getString(1).toLowerCase().equals("branch")) {
                    ps.execute("DROP TABLE branch");
                    break;
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
