// this is abstruct base class
// protected variables, constructor chaining (this()), method overloading

package core;

import util.TransactionStack;
import util.Logger;
import exception.BankExceptions.TransactionException;

public abstract class Account {

    private String accountId;
    protected double balance;
    protected TransactionStack history;

    public Account(String accountId) {
        this(accountId, 0.0); 
    }

    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.history = new TransactionStack();
        Logger.log("INFO", "Account created: " + this.accountId + " with balance " + this.balance);
    }

    public void deposit(double amount) {
        deposit(amount, "Standard Deposit");
    }

    public void deposit(double amount, String note) {
        if (amount <= 0) {
            throw new TransactionException("Deposit amount must be positive.");
        }
        this.balance += amount;
        String logMsg = note + " of $" + amount + ". New Balance: $" + this.balance;
        this.history.push(logMsg);
        Logger.log("INFO", "[ACC: " + this.accountId + "] " + logMsg);
    }

    public String getAccountId() {
        return this.accountId;
    }

    public double getBalance() {
        return this.balance;
    }

    public abstract void processMonthEnd();
}