package com.bank_account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service class that manages multiple BankAccount instances.
 * Stores accounts in an ArrayList (as required by the project spec).
 * Responsible for create/find/deposit/withdraw/show operations.
 */
public class BankManager {

    // Primary storage for accounts (allows multiple accounts)
    private final List<BankAccount> accounts = new ArrayList<>();

    public boolean createAccount(String name, String accountNumber, double initialBalance, String accountType) {
        // Basic validations
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name must not be empty.");
            return false;
        }
        if (accountNumber == null || !accountNumber.matches("\\d+")) {
            System.out.println("Error: Account number must contain digits only.");
            return false;
        }
        if (initialBalance < 0) {
            System.out.println("Error: Initial balance cannot be negative.");
            return false;
        }
        // Duplicate check
        if (findAccountByNumber(accountNumber) != null) {
            System.out.println("Error: Account number already exists. Try another number.");
            return false;
        }

        // Create and add account
        BankAccount acct = new BankAccount(name.trim(), accountNumber.trim(), initialBalance);
        if (accountType != null && !accountType.trim().isEmpty()) {
            acct.setAccountType(accountType.trim());
        }
        accounts.add(acct);
        System.out.println("Account created successfully.");
        return true;
    }

 
    public BankAccount findAccountByNumber(String accountNumber) {
        if (accountNumber == null) return null;
        String target = accountNumber.trim();
        for (BankAccount a : accounts) {
            if (a.getAccountNumber().equals(target)) return a;
        }
        return null;
    }

  
    public boolean depositToAccount(String accountNumber, double amount) {
        BankAccount a = findAccountByNumber(accountNumber);
        if (a == null) {
            System.out.println("Error: Account not found.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid amount. Deposit must be > 0.");
            return false;
        }
        boolean ok = a.deposit(amount);
        if (ok) {
            System.out.printf("Deposit successful. New Balance: %s%n", formatBalance(a.getBalance()));
            return true;
        } else {
            System.out.println("Deposit failed.");
            return false;
        }
    }

    /**
     * Withdraw amount from specified account.
 
     */
    public boolean withdrawFromAccount(String accountNumber, double amount) {
        BankAccount a = findAccountByNumber(accountNumber);
        if (a == null) {
            System.out.println("Error: Account not found.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: Invalid amount. Withdrawal must be > 0.");
            return false;
        }
        double current = a.getBalance();
        if (amount > current) {
            System.out.println("Error: Insufficient funds.");
            return false;
        }
        boolean ok = a.withdraw(amount);
        if (ok) {
            System.out.printf("Withdrawal successful. New Balance: %s%n", formatBalance(a.getBalance()));
            return true;
        } else {
            System.out.println("Withdrawal failed.");
            return false;
        }
    }

    /**
     * Show balance for the account number (prints formatted).
     
     */
    public Double showBalance(String accountNumber) {
        BankAccount a = findAccountByNumber(accountNumber);
        if (a == null) {
            System.out.println("Error: Account not found.");
            return null;
        }
        System.out.println("Account Number: " + a.getAccountNumber());
        System.out.println("Account Holder: " + a.getName());
        System.out.printf("Current Balance: %s%n", formatBalance(a.getBalance()));
        return a.getBalance();
    }

 
    public boolean showAccountDetails(String accountNumber) {
        BankAccount a = findAccountByNumber(accountNumber);
        if (a == null) {
            System.out.println("Error: Account not found.");
            return false;
        }
        a.displayDetails();
        return true;
    }

    /**
     * Return a copy of all accounts (defensive copy to avoid external modification).
     *
     * @return list of accounts
     */
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    /* ---------------------- Helpers ---------------------- */

    // Format balance as currency (reuse BankAccount's formatting if desired)
    private String formatBalance(double amt) {
        // simple rupee sign formatting with 2 decimals
        return String.format("₹%.2f", amt);
    }
    
}
