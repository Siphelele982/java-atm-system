package atm.system;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class ATMSYSTEM{
    private Scanner input = new Scanner(System.in);
    public void creatAccounts(){
        boolean isRunning = true;
        while(isRunning) {
            System.out.print("Enter account number: ");
            String accNum = input.nextLine();

            System.out.print("Enter PIN: ");
            String pinNum = input.nextLine();
            
            System.out.print("Enter how much balance you have R");     
            double balance;
            
            //HANDLING  EXCEPTION
            try {
               balance = Double.parseDouble(input.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Invalid numbers entered!");
                continue;
            }
                
            //VALIDATING ACCOUNT DETAILS
            boolean accValid = Pattern.matches("\\d{9}",accNum);
            boolean pinValid = Pattern.matches("\\d{4}",pinNum);

            if(!accValid) {
                System.out.println("Account numbers must be [9]!");
                continue;
            }
            if(!pinValid){
                System.out.println("Pin numbers must be [4]!");
                continue;
            }
            if(balance < 0) {
                System.out.println("Balance cant be negative!");
                continue;
            }      
            Account account = new Account(accNum,pinNum, balance);
            
            AccountDAO dao = new AccountDAO(); 
            
            boolean success = dao.createAccount(account); //CALLING THE METHOD WE JUST CREATED

            if(success) {
                
                isRunning  = false;
                
                System.out.println("Account has been created successfully");
                System.out.println("Account number: " + accNum);
                System.out.println("Balance: " + balance);
                
            }else{
                System.out.println("Account could not be created in the database.");
            }
            
            
    }
    }
    public Account logging(){
        boolean isRunning = true;

        while (isRunning) {

            System.out.print("Enter an account number: ");
            String accNum = input.nextLine().trim();

            System.out.print("Enter a pin number: ");
            String pinNum = input.nextLine().trim();

            // Validate account number
            boolean accValid = Pattern.matches("\\d{9}", accNum);

            // Validate PIN
            boolean pinValid = Pattern.matches("\\d{4}", pinNum);

            if (!accValid) {
                System.out.println();
                System.out.println("Please enter correct account number!");
                continue;
            }

            if (!pinValid) {
                System.out.println();
                System.out.println("Please enter correct pin!");
                continue;
            }

            // Create DAO
             AccountDAO dao = new AccountDAO();

            // Ask database to check account number + PIN
            Account account = dao.login(accNum, pinNum);

            if (account != null) { //if the account is not missing/empty do the following

            System.out.println();
            System.out.println("Login was successful!");

            return account; //send this method to whomever called me 

        } else {

            System.out.println();
            System.out.println("Account number or  pin is incorrect!");
            System.out.println("Please try again.");
        }
    }

    return null;
}
    
        
        
    public void main_menu() throws SQLException{ //NOW THE ACCOUNT HERE RECEIVE THE LOGGED IN USER
        
        boolean running = true;
        
        while(running){
            
            System.out.println("\n-------ATM-MENU----------");
            System.out.println("1.Create account");
            System.out.println("2.Login");
            System.out.println("3.Exit");
            System.out.println("--------------------------");
            
            System.out.print("Choose your option: ");
            String choice = input.nextLine();
            System.out.println("\n");
           switch(choice) {
                case "1":
                   creatAccounts();
                   break;
                           
                case "2":
                    Account currentAccount = logging();//give me the account logged-in
                    accMenu(currentAccount);  
                case "3":
                    running = false;
                    System.out.println("Thank you for choosing our bank ,byee!");
                    break;
                default:
                    System.out.println("Invalid input ,try again!");
           }
        }
    }    
    public void deposit(Account currentAccount) {
        System.out.print("How much you want to deposit? R ");
        double amount;
        
        try {
           amount = Double.parseDouble(input.nextLine());
        }catch(NumberFormatException e) {
           System.out.println("Please enter valid amount!");
           return;
        }
        AccountDAO dao = new AccountDAO();

        boolean success = dao.deposit(currentAccount.getAccNum(), amount);

        if (success) {
            currentAccount.deposit(amount);

            boolean transactionSaved = dao.addTransaction(currentAccount.getAccNum(),"DEPOSIT",amount);
        
        if (transactionSaved) {
            System.out.println("Money has been successfully deposited!");
            System.out.println("New balance: R" + currentAccount.getBalance());
        }else {
            System.out.println("Money deposited, but transaction history could not be saved");
        }
        }
    }
    
    public void withdraw(Account currentAccount) throws SQLException {
        
        System.out.print("How much you want to withdraw? ");
        double amount;
        
        try{
            amount = Double.parseDouble(input.nextLine().trim());
        }
        catch(NumberFormatException e) {
            System.out.println("Invalid input!");
            System.out.println("Please try again");
            return;
        }
        AccountDAO dao = new AccountDAO();  //WE ARE CREATING THE ACCOUNTDAO OBJECT 

        boolean success = dao.withdraw(currentAccount.getAccNum(), amount);

        if (success) {
            currentAccount.withdraw(amount);
            System.out.println("Withdrawal of R" + amount + " was successful!");
         
            boolean transactionSaved = dao.addTransaction(currentAccount.getAccNum(), "WITHDRAW",amount);

            if (transactionSaved) {
                System.out.println();
                
                System.out.println("Withdrawal succeeded!");

                System.out.println("Current balance: R" + currentAccount.getBalance());
            } else {
                System.out.println("Withdrawal successful, but transaction history could not be saved.");
            }
        System.out.println("Withdrawal failed!.");
       }
    }
   
    
    public void transfer(Account currentAccount) throws SQLException {
        
        System.out.println("Enter the receiver account number: ");
        String receiverAccNum = input.nextLine();
        
        if(currentAccount.getAccNum().equals(receiverAccNum)) {
            System.out.println("You cannot transfer money to yourself!");
            return;
        }
        System.out.println("Enter an amount to sender: ");
        double amount;
                
        try {   
           amount = Double.parseDouble(input.nextLine());  
        }catch(NumberFormatException e) {
            System.out.println("Invalid input ,try again please");
            return;
        }
        
        //PERFOMING ACTION OF TRANSFER
        AccountDAO dao = new AccountDAO();

        boolean success = dao.transfer(currentAccount.getAccNum(),receiverAccNum,amount);
        
        if (success) {       
            currentAccount.withdraw(amount); //updating also the java object
            System.out.println();
            System.out.println("Transfer of R" + amount + " was successsful");

            System.out.println("Reamaining balance: R" + currentAccount.getBalance());
            
            boolean transactionSaved = dao.addTransaction(currentAccount.getAccNum(),"TRANSFER", amount);
            
            if(transactionSaved) {
                System.out.println();
                
                System.out.println("Transfer succeeded!");
            }
            else {
                System.out.println("Transaction succeded ,but the history couldnt been save!");
            }
       }else {
            System.out.println();
            System.out.println("Transfer failed!");

    }
    }
    public void accMenu(Account currentAccount) throws SQLException{
         
       boolean isRunning = true;
       while(isRunning){
            System.out.println("\n-------ACCOUNT MENU--------");
            System.out.println("1.Check balance");
            System.out.println("2.Deposit");
            System.out.println("3.Withdraw");
            System.out.println("4.Transfer");
            System.out.println("5.Transaction history");
            System.out.println("6.Logging out");
            System.out.println("--------------------------");
            
            System.out.println("Choose your option: ");
            String choice = input.nextLine();
            
            
            switch(choice) {
                case "1":
                    
                    System.out.println("Current balance: " + "R" + currentAccount.getBalance()); 
                    break;
                case "2":
                   
                    deposit(currentAccount);
                    break;
                case "3":

                    withdraw(currentAccount);
                    break;
                case "4":
                    
                    transfer(currentAccount);
                    break;
                case "5": 
                    
                   AccountDAO dao = new AccountDAO();
                   dao.showTransactionHistory(currentAccount.getAccNum());
                    break;
                    
                case "6":
                    
                    isRunning =  false;
                    System.out.println("Logging out from account menu!");
                    break;
                default:
                    
                    System.out.println("Invalid input!");
            }
       }
    }

    public static void main(String[] args) throws SQLException {
       ATMSYSTEM obj = new ATMSYSTEM();
       
       obj.main_menu();
       
   }

    
}
