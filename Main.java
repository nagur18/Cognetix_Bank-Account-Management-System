package com.bank_account;

import java.util.Scanner;

/**
 * Console UI for the Bank Account system.
 * Uses BankManager for all business operations.
 */
public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final BankManager MANAGER = new BankManager();

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("BANK ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("==============================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1":
                    createAccountFlow();
                    break;
                case "2":
                    depositFlow();
                    break;
                case "3":
                    withdrawFlow();
                    break;
                case "4":
                    checkBalanceFlow();
                    break;
                case "5":
                    viewAccountDetailsFlow();
                    break;
                case "6":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number from 1 to 6.");
            }
        }
        SCANNER.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. View Account Details");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    /* ------------------- Flow handlers ------------------- */

    private static void createAccountFlow() {
        System.out.println("\n-- Create Account --");
        String name = askNonEmpty("Customer Name");
        String accountNumber = askAccountNumber("Account Number (digits only)");
        double initialBalance = askNonNegativeDouble("Initial Balance (>= 0)");

        System.out.print("Account Type (Savings/Current) [press Enter for Savings]: ");
        String accountTypeInput = SCANNER.nextLine().trim();
        String accountType = accountTypeInput.isEmpty() ? "Savings" : accountTypeInput;

        // Call manager
        MANAGER.createAccount(name, accountNumber, initialBalance, accountType);
    }

    private static void depositFlow() {
        System.out.println("\n-- Deposit Money --");
        String accountNumber = askAccountNumber("Enter Account Number");
        double amount = askPositiveDouble("Enter amount to deposit");
        MANAGER.depositToAccount(accountNumber, amount);
    }

    private static void withdrawFlow() {
        System.out.println("\n-- Withdraw Money --");
        String accountNumber = askAccountNumber("Enter Account Number");
        double amount = askPositiveDouble("Enter amount to withdraw");
        MANAGER.withdrawFromAccount(accountNumber, amount);
    }

    private static void checkBalanceFlow() {
        System.out.println("\n-- Check Balance --");
        String accountNumber = askAccountNumber("Enter Account Number");
        MANAGER.showBalance(accountNumber);
    }

    private static void viewAccountDetailsFlow() {
        System.out.println("\n-- View Account Details --");
        String accountNumber = askAccountNumber("Enter Account Number");
        MANAGER.showAccountDetails(accountNumber);
    }

    /* ------------------- Input helpers ------------------- */

    private static String askNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Error: Field cannot be empty. Please re-enter.");
        }
    }

    private static String askAccountNumber(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            if (input != null && input.trim().matches("\\d+")) {
                return input.trim();
            }
            System.out.println("Error: Account number must contain digits only. Please re-enter.");
        }
    }

    private static double askNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            try {
                double val = Double.parseDouble(input.trim());
                if (val < 0) {
                    System.out.println("Error: Value cannot be negative. Please re-enter.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number. Please enter a valid numeric value.");
            }
        }
    }

    private static double askPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String input = SCANNER.nextLine();
            try {
                double val = Double.parseDouble(input.trim());
                if (val <= 0) {
                    System.out.println("Error: Amount must be greater than zero. Please re-enter.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid amount. Please enter numeric digits only.");
            }
        }
    }
}
