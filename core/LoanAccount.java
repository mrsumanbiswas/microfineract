package microfineract.core;

import microfineract.model.BankingRules.Penalizable;
import microfineract.util.Logger;

public class LoanAccount extends Account implements Penalizable {

    private static final double LATE_FEE = 35.00;
    
    private double[][] repaymentSchedule;

    public LoanAccount(String accountId, double initialLoanAmount) {
        // In a loan, the balance represents the amount owed (debt)
        super(accountId, initialLoanAmount); 
        setupRepaymentSchedule();
    }


    private void setupRepaymentSchedule() {
        // 12 rows representing 12 months
        repaymentSchedule = new double[12][];
        
        // Month 0 (Index 0): Grace period -> 0 payments due
        repaymentSchedule[0] = new double[0];
        
        // Months 1-5: Standard schedule -> 1 payment of $100 due per month
        for (int i = 1; i <= 5; i++) {
            repaymentSchedule[i] = new double[] { 100.00 };
        }
        
        // Month 6: Accelerated bi-weekly schedule -> 2 payments of $50 due
        repaymentSchedule[6] = new double[] { 50.00, 50.00 };
        
        // Months 7-11: Standard schedule -> 1 payment of $100 due per month
        for (int i = 7; i < 12; i++) {
            repaymentSchedule[i] = new double[] { 100.00 };
        }
        
        Logger.log("INFO", "Jagged repayment schedule generated for loan: " + this.getAccountId());
    }

    @Override
    public void applyPenalty() {
        this.balance += LATE_FEE; // Increases the debt
        this.history.push("Penalty Applied: $" + LATE_FEE);
        Logger.log("WARN", "Late fee of $" + LATE_FEE + " applied to " + this.getAccountId());
    }

    @Override
    public void deposit(double amount, String note) {
        this.balance -= amount;
        String logMsg = note + " of $" + amount + ". Remaining Debt: $" + this.balance;
        this.history.push(logMsg);
        Logger.log("INFO", "[LOAN: " + this.getAccountId() + "] " + logMsg);
    }

    // Specific monthly processing for a loan account.
    @Override
    public void processMonthEnd() {
        Logger.log("INFO", "Month-end processed for Loan Account: " + this.getAccountId());
    }
}