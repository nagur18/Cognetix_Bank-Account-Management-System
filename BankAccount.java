package com.bank_account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * POJO representing a single bank account.
 * Simple, safe operations: deposit, withdraw, getters, display.
 */
public class BankAccount {

    private String name;
    private String accountNumber;   // store as String to preserve leading zeros
    private double balance;
    private LocalDate joinDate;
    private String accountType;     // e.g. "Savings" or "Current"

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final NumberFormat CURRENCY_FMT = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    
    public BankAccount(String name, String accountNumber, double initialBalance, LocalDate joinDate, String accountType) {
        // basic null/empty guards (more validation belongs to BankManager/UI)
        this.name = Objects.requireNonNull(name, "name must not be null").trim();
        this.accountNumber = Objects.requireNonNull(accountNumber, "accountNumber must not be null").trim();

        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;

        this.joinDate = (joinDate == null) ? LocalDate.now() : joinDate;
        this.accountType = (accountType == null || accountType.trim().isEmpty()) ? "Savings" : accountType.trim();
    }

    /**
     * Convenience constructor: no joinDate/accountType provided.
     */
    public BankAccount(String name, String accountNumber, double initialBalance) {
        this(name, accountNumber, initialBalance, LocalDate.now(), "Savings");
    }

    /* ---------------------- Business methods ---------------------- */

    /**
     * Deposit amount into account.
     *
     * @param amount must be > 0
     * @return true if deposit succeeded, false otherwise
     */
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        return true;
    }

    /**
     * Withdraw amount from account.
     *
     * @param amount must be > 0 and <= current balance
     * @return true if withdrawal succeeded, false if invalid amount or insufficient funds
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) return false;
        if (amount > this.balance) return false; // insufficient funds
        this.balance -= amount;
        return true;
    }

    /* ---------------------- Getters / Setters ---------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name).trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // accountNumber is intentionally not given a public setter to keep uniqueness/integrity

    public double getBalance() {
        return balance;
    }

    // For safety, avoid a public setBalance; use deposit/withdraw for changes.

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = (accountType == null || accountType.trim().isEmpty()) ? this.accountType : accountType.trim();
    }

    /* ---------------------- Display / Utility ---------------------- */

    /**
     * Nicely print account details to console (used by BankManager/Main).
     */
    public void displayDetails() {
        System.out.println("===============================");
        System.out.println("ACCOUNT DETAILS");
        System.out.println("===============================");
        System.out.printf("Account Holder : %s%n", this.name);
        System.out.printf("Account Number : %s%n", this.accountNumber);
        System.out.printf("Account Type   : %s%n", this.accountType);
        System.out.printf("Join Date      : %s%n", this.joinDate.format(DATE_FMT));
        System.out.printf("Current Balance: %s%n", formatCurrency(this.balance));
        System.out.println("-------------------------------");
    }

    private static String formatCurrency(double amount) {
        // NumberFormat for India locale prints ₹ and two decimal places
        return CURRENCY_FMT.format(amount);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "name='" + name + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + formatCurrency(balance) +
                ", joinDate=" + joinDate +
                ", accountType='" + accountType + '\'' +
                '}';
    }
    
  

}
