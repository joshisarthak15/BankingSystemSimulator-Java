package com.bankingsystem;

import com.bankingsystem.service.AccountService;
import com.bankingsystem.exception.*;
import com.bankingsystem.model.Account;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountService service = new AccountService();
        boolean mainRunning = true;

        System.out.println("=== Welcome to Banking System Simulator ===");

        while (mainRunning) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Perform Operations on Existing Account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String mainChoice = sc.nextLine();

            switch (mainChoice) {
                case "1" -> {
                    System.out.print("Enter account holder name: ");
                    String name = sc.nextLine();
                    try {
                        Account acc = service.createAccount(name);
                        System.out.println("Account created successfully! Account Number: " + acc.getAccountNumber());
                    } catch (InvalidNameException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case "2" -> {
                    System.out.print("Enter your Account Number: ");
                    String accNum = sc.nextLine();
                    try {
                        Account acc = service.getAccount(accNum);
                        if (acc == null) {
                            throw new AccountNotFoundException("Account not found. Please create an account first.");
                        }
                        boolean accountOpsRunning = true;
                        while (accountOpsRunning) {
                            System.out.println("\n--- Account Operations Menu ---");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer");
                            System.out.println("4. Show Balance");
                            System.out.println("5. Return to Main Menu");
                            System.out.print("Enter your choice: ");
                            String subChoice = sc.nextLine();

                            switch (subChoice) {
                                case "1" -> {
                                    System.out.print("Enter amount to deposit: ");
                                    double amt = Double.parseDouble(sc.nextLine());
                                    try {
                                        service.deposit(accNum, amt);
                                        System.out.println("Deposit successful!");
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case "2" -> {
                                    System.out.print("Enter amount to withdraw: ");
                                    double amt = Double.parseDouble(sc.nextLine());
                                    try {
                                        service.withdraw(accNum, amt);
                                        System.out.println("Withdrawal successful!");
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case "3" -> {
                                    System.out.print("Enter destination Account Number: ");
                                    String toAcc = sc.nextLine();
                                    System.out.print("Enter amount to transfer: ");
                                    double amt = Double.parseDouble(sc.nextLine());
                                    try {
                                        service.transfer(accNum, toAcc, amt);
                                        System.out.println("Transfer successful!");
                                    } catch (Exception e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case "4" -> {
                                    try {
                                        service.showBalance(accNum);
                                    } catch (AccountNotFoundException e) {
                                        System.out.println("Error: " + e.getMessage());
                                    }
                                }
                                case "5" -> accountOpsRunning = false; // return to main
                                default -> System.out.println("Invalid option, try again.");
                            }
                        }
                    } catch (AccountNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case "3" -> {
                    System.out.println("Thank you for using Banking System Simulator. Goodbye!");
                    mainRunning = false;
                }

                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}
