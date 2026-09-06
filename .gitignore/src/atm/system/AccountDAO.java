package atm.system;


import atm.system.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public boolean createAccount(Account account) {

        String sql = "INSERT INTO account (acc_num, pin_num, balance) VALUES (?, ?, ?)";

        try (Connection connection = DBCONNECTION.getconnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccNum());
            statement.setString(2, account.getPinNum());
            statement.setDouble(3, account.getBalance());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Could not create account.");
            e.printStackTrace();
            return false;
        }
        
    }
        
    public Account login(String accNum, String pinNum) {

        String sql = "SELECT acc_num, pin_num, balance "
                    + "FROM account "
                    + "WHERE acc_num = ? AND pin_num = ?";

        try (Connection connection = DBCONNECTION.getconnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accNum);
            statement.setString(2, pinNum);

        ResultSet result = statement.executeQuery();

        if (result.next()) {

            String accountNumber = result.getString("acc_num");
            String pin = result.getString("pin_num");
            double balance = result.getDouble("balance");

            return new Account(accountNumber, pin, balance);
        }
        }catch (SQLException e) {

        System.out.println("Cannot login!.");
        e.printStackTrace();
    }

        return null;
}

    public boolean deposit(String accNum, double amount) {

        String sql = "UPDATE account "
            + "SET balance = balance + ? "
            + "WHERE acc_num = ?";

        try (Connection connection = DBCONNECTION.getconnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setString(2, accNum);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        }catch (SQLException e) {

            System.out.println("Could not deposit money.");
            e.printStackTrace();

            return false;
    }
}
    public boolean withdraw(String accNum, double amount) {

        String sql = "UPDATE account "
               + "SET balance = balance - ? "
               + "WHERE acc_num = ? "
               + "AND balance >= ?";

        try (Connection connection = DBCONNECTION.getconnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setString(2, accNum);
            statement.setDouble(3, amount);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

       }catch (SQLException e) {
            System.out.println("Could not withdraw money.");
            e.printStackTrace();
            return false;
        }
}
    public boolean transfer(String senderAccNum, String receiverAccNum, double amount) throws SQLException {

        String withdrawSQL =
            "UPDATE account " +
            "SET balance = balance - ? " +
            "WHERE acc_num = ? " +
            "AND balance >= ?";

        String depositSQL =
            "UPDATE account " +
            "SET balance = balance + ? " +
            "WHERE acc_num = ?";

        Connection connection = null;

        try {

            connection = DBCONNECTION.getconnection();
            
            connection.setAutoCommit(false);

            try (PreparedStatement withdrawStatement = connection.prepareStatement(withdrawSQL)) {

            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setString(2, senderAccNum);
            withdrawStatement.setDouble(3, amount);

            int senderUpdated = withdrawStatement.executeUpdate();

            if (senderUpdated == 0) {
                connection.rollback();
                return false;
            }
            }
        try (PreparedStatement depositStatement = connection.prepareStatement(depositSQL)) {

            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, receiverAccNum);
            int receiverUpdated = depositStatement.executeUpdate();

            if (receiverUpdated == 0) {
                connection.rollback();
                return false;
            }
            }
        connection.commit();
        return true;
        }catch (SQLException e) {
            System.out.println("Transfer failed!");
            e.printStackTrace();
            return false;
        }
        finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        }
    public boolean addTransaction(String accNum, String type, double amount) {

    String sql = "INSERT INTO transactions "
               + "(acc_num, transaction_type, amount) "
               + "VALUES (?, ?, ?)";

    try (Connection connection = DBCONNECTION.getconnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, accNum);
        statement.setString(2, type);
        statement.setDouble(3, amount);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (SQLException e) {

        System.out.println("Could not save transaction.");
        e.printStackTrace();

        return false;
    }
}
    public void showTransactionHistory(String accNum) {

        String sql = "SELECT transaction_type, amount, transaction_date "
               + "FROM transactions "
               + "WHERE acc_num = ? "
               + "ORDER BY transaction_date DESC";

        try (Connection connection = DBCONNECTION.getconnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, accNum);

            ResultSet result = statement.executeQuery();

            System.out.println("\n------- TRANSACTION HISTORY -------");

            boolean hasTransactions = false;

            while (result.next()) {

                hasTransactions = true;

                String type = result.getString("transaction_type");
                double amount = result.getDouble("amount");
                String date = result.getString("transaction_date");

                System.out.println(type + " | R" + amount + " | " + date);
            }
            if (!hasTransactions) {
                System.out.println("No transactions yet.");
            }
        System.out.println("-----------------------------------");

        }catch (SQLException e) {
            System.out.println("Could not retrieve transaction history.");
            e.printStackTrace();
        }   
    }
}
