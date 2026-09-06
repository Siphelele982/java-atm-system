package atm.system;
import java.util.ArrayList;
import java.util.Scanner;


public class Account{
    private String accNum;
    private String pinNum;
    private double balance;    
    
    private Scanner input = new Scanner(System.in);
    public  Account(String accNum,String pinNum ,double balance) {
        this.accNum = accNum;
        this.pinNum = pinNum;
        this.balance = balance;
     
    }
    public String getAccNum() {
        return accNum;
    }
    public String getPinNum() {
        return pinNum;
}
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance) {
        this.balance  = balance;
    }
    //IS THIS OPERATION ALLOWED ,HOW DOES IT CHANGES MY BALANCE
    public boolean withdraw(double amount) {

    if (amount <= 0) {
        return false;
    }
    if (amount > balance) {
        return false;
    }
    else {
        balance -= amount;
        return true;
    }
 }
   public boolean deposit(double amount) {
        
    if(amount <= 0) {
        return false;
    }else {
        balance += amount;
            
        return true;
   }
   }
   public boolean transfer(Account accountReciever, double amount) {

    if (amount <= 0) {
        System.out.println("Money cant be negative!");
        return false;
    }
    else if(amount > balance) {
        System.out.println("Insufficient funds");
        return false;
    }
    if(accountReciever == this) {
        return false;
    }

    balance -= amount;
    accountReciever.balance += amount;
    
        return true;
}
}

//NOTE THE ATMSYSTEM HANDLE THE USER INTERACTION WHILE THE ,ACCOUNT HANDLE THE LOGIC OF MONEY 