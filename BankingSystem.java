
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Account class to represent different types of accounts
class Account {
    private String accountNumber;
    private String accountType;
    private double balance;

    public Account(String accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = 0.0; // Initialize balance to 0
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}

// User class to represent bank users
class User {
    private String username;
    private String password;
    private int pin;
    private Map<String, Account> accounts;

    public User(String username, String password, int pin) {
        this.username = username;
        this.password = password;
        this.pin = pin;
        this.accounts = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public int getPin() {
        return pin;
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts.values());
    }
}

// BankingSystem class to manage users and accounts
public class BankingSystem {
    private Map<String, User> users;

    public BankingSystem() {
        this.users = new HashMap<>();
    }

    public void registerUser(String username, String password, int pin) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, pin));
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }

    public void createAccount(User user, String accountNumber, String accountType) {
        Account account = new Account(accountNumber, accountType);
        user.addAccount(account);
        System.out.println("Account created successfully.");
    }

    public void deposit(User user, String accountNumber, double amount) {
        Account account = user.getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(User user, String accountNumber, double amount) {
        Account account = user.getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(User user, String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = user.getAccount(fromAccountNumber);
        Account toAccount = user.getAccount(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public double checkBalance(User user, String accountNumber) {
        Account account = user.getAccount(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        return -1; // Account not found
    }

    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        // Sample usage
        bankingSystem.registerUser("user1", "password1", 1234);
        User user = bankingSystem.login("user1", "password1");
        if (user != null) {
            bankingSystem.createAccount(user, "123456", "Savings");
            bankingSystem.deposit(user, "123456", 1000);
            bankingSystem.createAccount(user, "789012", "Checking");
            bankingSystem.transfer(user, "123456", "789012", 500);
            double balance = bankingSystem.checkBalance(user, "789012");
            System.out.println("Current balance: $" + balance);
        } else {
            System.out.println("Invalid username or password.");
        }

        scanner.close();
    }
}


