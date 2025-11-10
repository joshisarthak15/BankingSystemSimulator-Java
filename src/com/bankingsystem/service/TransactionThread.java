package com.bankingsystem.service;

public class TransactionThread extends Thread{
    private final AccountService service;
    private final String accNum;
    private final double amt;
    private boolean deposit;

    public TransactionThread(AccountService service, String accNum, double amt, boolean deposit) {
        this.service = service;
        this.accNum = accNum;
        this.amt = amt;
        this.deposit = deposit;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " started.");
            Thread.sleep(1000); // simulate delay
            if (deposit)
                service.deposit(accNum, amt);
            else
                service.withdraw(accNum, amt);
            System.out.println(Thread.currentThread().getName() + " completed transaction: " +
                    (deposit ? "Deposited " : "Withdrew ") + amt);
        } catch (Exception e) {
            System.out.println("Transaction failed on " + accNum + ": " + e.getMessage());
        }
    }
}
