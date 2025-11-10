package com.bankingsystem.Main;
import com.bankingsystem.exception.InvalidNameException;
import com.bankingsystem.model.Account;
import com.bankingsystem.service.AccountService;
import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.service.TransactionThread;

import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();


        System.out.println("Welcome to Banking System Simulator");


        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1 -> { // Create
                        System.out.print("Enter full name: ");
                        String name = sc.nextLine();
                        try {
                            Account acc = service.createAccount(name);
                            System.out.println("Account created: " + acc.getAccountNumber());
                        } catch (InvalidNameException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 2 -> { // Deposit
                        System.out.print("Enter Account Number: ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to deposit: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        try {
                            service.deposit(accNum, amt);
                            System.out.println("Deposit successful");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 3 -> { // Withdraw
                        System.out.print("Enter Account Number: ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter amount to withdraw: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        try {
                            service.withdraw(accNum, amt);
                            System.out.println("Withdrawal successful");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 4 -> { // Transfer
                        System.out.print("From Account Number: ");
                        String from = sc.nextLine();
                        System.out.print("To Account Number: ");
                        String to = sc.nextLine();
                        System.out.print("Amount: ");
                        double amt = Double.parseDouble(sc.nextLine());
                        try {
                            service.transfer(from, to, amt);
                            System.out.println("Transfer successful");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 5 -> { // Show balance
                        System.out.print("Enter Account Number: ");
                        String accNum = sc.nextLine();
                        try {
                            service.showBalance(accNum);
                        } catch (AccountNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 6 -> { // List accounts (for debug/demo)
                        System.out.println("All accounts:");
                        service.getAllAccounts().values().forEach(System.out::println);
                    }
                    case 7 -> { // Multithread demo
                        System.out.print("Enter Account Number for demo: ");
                        String accNum = sc.nextLine();
                        System.out.print("Enter initial deposit amount: ");
                        double init = Double.parseDouble(sc.nextLine());
                        try {
                            service.deposit(accNum, init);
                        } catch (Exception ignored) {}
                        Thread t1 = new TransactionThread(service, accNum, 100, true);
                        Thread t2 = new TransactionThread(service, accNum, 50, false);
                        t1.start();
                        t2.start();
                        try { t1.join(); t2.join(); } catch (InterruptedException ignored) {}
                        try { service.showBalance(accNum); } catch (AccountNotFoundException ignored) {}
                    }
                    case 8 -> {
                        System.out.println("Exiting. Thank you!");
                        sc.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input. Please enter numbers for menu choices.");
            }
        }
    }


    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Show Balance");
        System.out.println("6. List All Accounts");
        System.out.println("7. Multithreading Demo (deposit & withdraw)");
        System.out.println("8. Exit");
    }
}


