// extend Account and implement BankingRules.InterestBearing (meth @Override)

package microfineract.core;

import microfineract.model.BankingRules.InterestBearing;
import microfineract.util.Logger;

public class SavingsAccount extends Account implements InterestBearing {
    
    // 4% annual interest
    private static final double ANNUAL_INTEREST_RATE = 0.04; 

    public SavingsAccount(String accountId, double initialBalance) {
        super(accountId, initialBalance);
    }

    @Override
    public void calculateInterest() {
        // Calculate 1 month of interest
        double monthlyYield = (this.balance * ANNUAL_INTEREST_RATE) / 12;
        
        // Uses the overloaded deposit method from the parent class
        this.deposit(monthlyYield, "Monthly Interest Yield");
    }

    @Override
    public void processMonthEnd() {
        calculateInterest();
        Logger.log("INFO", "Month-end processed for Savings Account: " + this.getAccountId());
    }
}