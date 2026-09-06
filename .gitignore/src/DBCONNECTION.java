
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBCONNECTION {

    public static Connection getconnection() {

        String url = "jdbc:mysql://localhost:3306/atm_system";
        String username = "root";
        String password = "Tolibadi@123#";

        try {

            Connection connection = DriverManager.getConnection(
                url,
                username,
                password
            );

            System.out.println("Connection to database succeeded!");

            return connection;

        } catch (SQLException e) {

            System.out.println("Connection to database failed!");
            e.printStackTrace();

            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println("Testing MySQL connection...");

        Connection connection = getconnection();

        if (connection != null) {
            System.out.println("MYSQL CONNECTION WORKED!");
        } else {
            System.out.println("MYSQL CONNECTION FAILED!");
        }
    }
}