import java.sql.*;

public class  Slip24{
    static final String DB_URL = "jdbc:odbc:EmployeeDB";
    static final String USER = "";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database");

            int choice;
            do {
                System.out.println("\nEmployee Management System Menu:");
                System.out.println("1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. Search");
                System.out.println("5. Display");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(System.console().readLine());

                switch (choice) {
                    case 1:
                        insertEmployee(conn);
                        break;
                    case 2:
                        updateEmployee(conn);
                        break;
                    case 3:
                        deleteEmployee(conn);
                        break;
                    case 4:
                        searchEmployee(conn);
                        break;
                    case 5:
                        displayEmployees(conn);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 6);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    static void insertEmployee(Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Emp (ENo, EName, salary, Desg) VALUES (?, ?, ?, ?)")) {
            System.out.println("Enter employee number:");
            int eno = Integer.parseInt(System.console().readLine());
            System.out.println("Enter employee name:");
            String ename = System.console().readLine();
            System.out.println("Enter employee salary:");
            double salary = Double.parseDouble(System.console().readLine());
            System.out.println("Enter employee designation:");
            String desg = System.console().readLine();

            pstmt.setInt(1, eno);
            pstmt.setString(2, ename);
            pstmt.setDouble(3, salary);
            pstmt.setString(4, desg);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Employee inserted successfully!");
            } else {
                System.out.println("Failed to insert employee.");
            }
        }
    }

    static void updateEmployee(Connection conn) throws SQLException {
        System.out.println("Enter employee number to update:");
        int eno = Integer.parseInt(System.console().readLine());
        System.out.println("Enter new salary:");
        double salary = Double.parseDouble(System.console().readLine());

        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE Emp SET salary = ? WHERE ENo = ?")) {
            pstmt.setDouble(1, salary);
            pstmt.setInt(2, eno);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No such employee found.");
            }
        }
    }

    static void deleteEmployee(Connection conn) throws SQLException {
        System.out.println("Enter employee number to delete:");
        int eno = Integer.parseInt(System.console().readLine());

        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Emp WHERE ENo = ?")) {
            pstmt.setInt(1, eno);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("No such employee found.");
            }
        }
    }

    static void searchEmployee(Connection conn) throws SQLException {
        System.out.println("Enter employee number to search:");
        int eno = Integer.parseInt(System.console().readLine());

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Emp WHERE ENo = ?")) {
            pstmt.setInt(1, eno);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Employee found:");
                    System.out.println("ENo: " + rs.getInt("ENo"));
                    System.out.println("EName: " + rs.getString("EName"));
                    System.out.println("Salary: " + rs.getDouble("salary"));
                    System.out.println("Designation: " + rs.getString("Desg"));
                } else {
                    System.out.println("No such employee found.");
                }
            }
        }
    }

    static void displayEmployees(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM Emp";
            try (ResultSet rs = stmt.executeQuery(sql)) {
                System.out.println("Employee List:");
                while (rs.next()) {
                    System.out.println("ENo: " + rs.getInt("ENo"));
                    System.out.println("EName: " + rs.getString("EName"));
                    System.out.println("Salary: " + rs.getDouble("salary"));
                    System.out.println("Designation: " + rs.getString("Desg"));
                    System.out.println("---------------------");
                }
            }
        }
    }
}