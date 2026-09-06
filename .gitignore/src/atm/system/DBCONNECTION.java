package atm.system;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBCONNECTION {

    public static Connection getconnection() {

        String url = "jdbc:mysql://localhost:3306/atm_system";
        String username = "root";
        String password = "Password";

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            return connection;
            
        } catch (SQLException e) {

            System.out.println("Connection to database failed!");
            e.printStackTrace();
            return null;
        }
    }
    
}
