package com.bankingsystem.service;

import com.bankingsystem.exception.AccountNotFoundException;
import com.bankingsystem.exception.InsufficientBalanceException;
import com.bankingsystem.exception.InvalidAmountException;
import com.bankingsystem.exception.InvalidNameException;
import com.bankingsystem.model.Account;
import com.bankingsystem.util.AccountNumberGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String name) throws InvalidNameException {
        if (name == null || name.trim().isEmpty())
            throw new InvalidNameException("Name cannot be empty");


        String accNum = AccountNumberGenerator.generate(name);
        // ensure uniqueness if collision
        while (accounts.containsKey(accNum)) {
            accNum = AccountNumberGenerator.generate(name);
        }


        Account acc = new Account(accNum, name);
        accounts.put(accNum, acc);
        return acc;
    }


    public void deposit(String accNum, double amt) throws AccountNotFoundException, InvalidAmountException {
        Account acc = accounts.get(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        if (amt <= 0) throw new InvalidAmountException("Deposit amount must be positive");
        acc.deposit(amt);
    }


    public void withdraw(String accNum, double amt) throws AccountNotFoundException, InsufficientBalanceException, InvalidAmountException {
        Account acc = accounts.get(accNum);
        if (acc == null) throw new AccountNotFoundException("Account not found: " + accNum);
        if (amt <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        synchronized (acc) {
            if (amt > acc.getBalance()) throw new InsufficientBalanceException("Insufficient balance");
            acc.withdraw(amt);
        }
    }


    public void transfer(String fromAcc, String toAcc, double amt)
            throws AccountNotFoundException, InsufficientBalanceException, InvalidAmountException {
        if (amt <= 0) throw new InvalidAmountException("Transfer amount must be positive");
        Account src = accounts.get(fromAcc);
        Account dest = accounts.get(toAcc);
        if (src == null) throw new AccountNotFoundException("Source account not found: " + fromAcc);
        if (dest == null) throw new AccountNotFoundException("Destination account not found: " + toAcc);


        // Lock ordering to avoid deadlocks: lock smaller hashcode first
        Account first = src.hashCode() <= dest.hashCode() ? src : dest;
        Account second = first == src ? dest : src;


        synchronized (first) {
            synchronized (second) {
                if (amt > src.getBalance()) throw new InsufficientBalanceException("Insufficient balance in source account");
                src.withdraw(amt);
                dest.deposit(amt);
            }
        }
    }


    public Account getAccount(String accNum) {
        return accounts.get(accNum);
    }


    public void showBalance(String accNum) throws AccountNotFoundException {
        Account acc = accounts.get(accNum);
        if (acc == null)
            throw new AccountNotFoundException("Account not found: " + accNum);
        System.out.println(acc);
    }

    public List<Account> searchByName(String name) {
        return accounts.values().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }


    public Map<String, Account> getAllAccounts() {
        return Collections.unmodifiableMap(accounts);
    }
}
