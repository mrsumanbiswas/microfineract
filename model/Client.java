// build the customer profile
// use varargs here public void assignAccounts(Account... accounts)

package microfineract.model;

import microfineract.core.Account;
import microfineract.util.Logger;
import microfineract.exception.BankExceptions.TransactionException;
import java.time.LocalDate;

public class Client {
    private String clientId;
    private String name;
    
    private LocalDate joinDate; 
    
    private Account[] accounts; 
    private int accountCount;

    public Client(String clientId, String name) {
        this.clientId = clientId;
        this.name = name;
        this.joinDate = LocalDate.now();
        this.accounts = new Account[5]; // Fixed limit of 5 accounts per client
        this.accountCount = 0;
        Logger.log("INFO", "New Client Profile Created: " + this.name + " [" + this.clientId + "]");
    }

    public void addAccounts(Account... newAccounts) {
        for (Account acc : newAccounts) {
            if (accountCount >= accounts.length) {
                throw new TransactionException("Maximum account limit reached for client: " + this.name);
            }
            accounts[accountCount++] = acc;
            Logger.log("INFO", "Account " + acc.getAccountId() + " linked to Client " + this.clientId);
        }
    }

    public String getName() { return name; }
    public Account[] getAccounts() { return accounts; }
    public int getAccountCount() { return accountCount; }
}