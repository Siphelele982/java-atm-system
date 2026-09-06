**Java ATM System**

A console-based ATM application developed in Java that simulates basic banking operations. The system allows users to create accounts, securely log in, manage their balances, transfer money between accounts, and view their transaction history.

The application uses **MySQL** to store and manage account data, making information persistent even after the application is closed.

**Features**

* Create a bank account
* Account number and PIN validation
* User login authentication
* Check account balance
* Deposit money
* Withdraw money
* Transfer money between accounts
* View transaction history
* Store account information in a MySQL database
* Retrieve account information from the database
* Logout

Concepts Used

* Classes and Objects
* Encapsulation
* Object-Oriented Programming (OOP)
* Methods
* ArrayList
* Loops
* Conditional Statements
* Input Validation
* Object Interaction
* Exception Handling
* JDBC
* SQL
* Database Connectivity
* CRUD Operations

**Main Classes**

**ATMSYSTEM.java**
Contains the main ATM application logic, menus, user interaction, and banking operations.

**Account.java**
Represents an ATM account and contains account-related information and methods.

**AccountDAO.java**
Handles communication between the Java application and the MySQL database, including creating accounts, logging in, and retrieving or updating account information.

**DBCONNECTION.java**
Establishes the connection between the Java application and the MySQL database using JDBC.

How It Works
When the program starts, the user is presented with the main ATM menu:

ATM MENU
1. Create Account
2. Login
3. Exit

**Create Account**
The user enters their account number, pin, and initial balance. The account information is then stored in the MySQL database.

Login
The user enters their account number and pin the system validates the credentials against the information stored in the database.

After successfully logging in, the user can access the account menu:

ACCOUNT MENU
1. Check Balance
2. Deposit
3. Withdraw
4. Transfer
5. Transaction History
6. Logout

Database
The application uses **MySQL** as its database and **JDBC** to allow Java to communicate with MySQL.
The database is responsible for storing persistent account information instead of keeping all account data only in memory.

Technologies Used
* Java
* MySQL
* JDBC (Java Database Connectivity)
* SQL
* Java Collections Framework
* Object-Oriented Programming (OOP)
* NetBeans IDE
* Git & GitHub

Database

The application connects to a local MySQL database using JDBC.

Example connection:

Java Application -- JDBC -- MySQL -- ATM Database

Future Improvements

- Change PIN functionality
- Improve error handling and validation
- Add password or PIN hashing and stronger authentication
- Add a graphical user interface using JavaFX
- Add more advanced transaction management
- Add an administrator interface
- Add account deletion functionality
- Add account statement generation
- Improve database security
- Deploy the application using a remote database

Author 
**Siphelele Tolibadi**
