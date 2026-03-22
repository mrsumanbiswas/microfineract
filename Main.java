// This is the entry point
// String manipulation, string builder (to purse CLI commands)
// Auto-Grader Setup (as sir mentioned to do predefined data based)
// Garbage collection

package microfineract;

import model.Client;
import core.Account;
import core.SavingsAccount;
import core.LoanAccount;
import util.Logger;
import exception.BankExceptions.TransactionException;

import java.util.Scanner;

/**
 * The entry point for the MicroFineract CLI application.
 */
public class Main {

    private static Client[] bankDatabase = new Client[10];
    private static int totalClients = 0;

    public static void main(String[] args) {
        Logger.log("INFO", "System Booting Up...");
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== MicroFineract Core Banking System ===");
        System.out.println("1. Interactive CLI Mode");
        System.out.println("2. Auto-Grader / Hardcoded Test Mode");
        System.out.print("Select mode (1 or 2): ");
        
        String mode = scanner.nextLine();

        if (mode.trim().equals("2")) {
            runAutomatedTests();
        } else {
            runInteractiveCLI(scanner);
        }
        
        scanner.close();
        Logger.log("INFO", "System Shutting Down.");
    }

    private static void runInteractiveCLI(Scanner scanner) {
        boolean running = true;
        System.out.println("\nAvailable Commands:");
        System.out.println("- CREATE_CLIENT [id] [name]");
        System.out.println("- DEPOSIT [acc_id] [amount] [optional_note]");
        System.out.println("- MONTH_END");
        System.out.println("- REPORT");
        System.out.println("- GC_CLEANUP");
        System.out.println("- EXIT");

        while (running) {
            System.out.print("\nmicrofineract> ");
            String input = scanner.nextLine();
            
            if (input.trim().equalsIgnoreCase("EXIT")) {
                running = false;
                continue;
            }
            
            try {
                processCommand(input);
            } catch (TransactionException e) {
                System.err.println("Command Error: " + e.getMessage());
                Logger.log("ERROR", e.getMessage());
            } catch (Exception e) {
                System.err.println("System Error: " + e.getMessage());
            }
        }
    }

    /**
     * REQUIREMENT: Automated testing with extensive hardcoded data.
     * This sequence proves overloading, 1D/2D arrays, and polymorphism.
     */
    private static void runAutomatedTests() {
        System.out.println("\n--- RUNNING EXPANDED EVALUATION SEED ---");
        
        String[] testCommands = {
            "CREATE_CLIENT 001 Suman",
            "CREATE_CLIENT 002 CmdLineCoding",
            
            // Proving standard deposit (Overload 1)
            "DEPOSIT SAV-001 2500.00", 
            
            // Proving deposit with note (Overload 2)
            "DEPOSIT SAV-002 8220.00 Hackathon_Sponsorship_Funds", 
            
            // Proving polymorphic loan repayment (Decreases debt)
            "DEPOSIT LOAN-001 100.00 First_Installment",
            
            // Advancing time to prove interfaces and jagged arrays
            "MONTH_END", 
            "MONTH_END",
            
            "REPORT",
            "GC_CLEANUP"
        };

        for (String cmd : testCommands) {
            System.out.println("\n> Executing: " + cmd);
            processCommand(cmd);
        }
        System.out.println("\n--- AUTOMATED EVALUATION COMPLETE ---");
    }

    private static void processCommand(String rawCommand) {
        String commandString = new String(rawCommand.trim()); 
        String[] tokens = commandString.split(" "); 
        String action = tokens[0].toUpperCase();

        switch (action) {
            case "CREATE_CLIENT":
                if (tokens.length < 3) throw new TransactionException("Missing client details.");
                Client newClient = new Client(tokens[1], tokens[2]);
                
                SavingsAccount savings = new SavingsAccount("SAV-" + tokens[1], 0.00);
                LoanAccount loan = new LoanAccount("LOAN-" + tokens[1], 10000.00); // 10k Debt
                
                newClient.addAccounts(savings, loan);
                bankDatabase[totalClients++] = newClient;
                System.out.println("Client created: " + tokens[2]);
                break;

            case "DEPOSIT":
                if (tokens.length < 3) throw new TransactionException("Usage: DEPOSIT [ACC_ID] [AMOUNT] [NOTE]");
                Account targetAccount = findAccount(tokens[1]);
                double amount = Double.parseDouble(tokens[2]);
                
                // REQUIREMENT: Method Overloading demonstration
                if (tokens.length >= 4) {
                    // Reconstruct the note if it has multiple words
                    String note = commandString.substring(commandString.indexOf(tokens[3]));
                    targetAccount.deposit(amount, note);
                } else {
                    targetAccount.deposit(amount);
                }
                break;

            case "MONTH_END":
                triggerMonthEnd();
                break;

            case "REPORT":
                generateReport();
                break;

            case "GC_CLEANUP":
                forceGarbageCollection();
                break;

            default:
                throw new TransactionException("Unknown command: " + action);
        }
    }

    /**
     * Helper method to search the 1D arrays for a specific account.
     */
    private static Account findAccount(String accountId) {
        for (int i = 0; i < totalClients; i++) {
            Client c = bankDatabase[i];
            for (int j = 0; j < c.getAccountCount(); j++) {
                Account acc = c.getAccounts()[j];
                if (acc != null && acc.getAccountId().equalsIgnoreCase(accountId)) {
                    return acc;
                }
            }
        }
        throw new TransactionException("Account not found: " + accountId);
    }

    private static void triggerMonthEnd() {
        for (int i = 0; i < totalClients; i++) {
            Client c = bankDatabase[i];
            for (int j = 0; j < c.getAccountCount(); j++) {
                Account acc = c.getAccounts()[j];
                
                acc.processMonthEnd(); 
                
                if (acc instanceof LoanAccount) {
                    LoanAccount specificLoan = (LoanAccount) acc;
                    specificLoan.applyPenalty(); 
                }
            }
        }
        System.out.println("Month-end processing complete. Interest paid and penalties applied.");
    }

    private static void generateReport() {
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder.append("\n=== DAILY BANKING REPORT ===\n");
        
        for (int i = 0; i < totalClients; i++) {
            Client c = bankDatabase[i];
            reportBuilder.append("Client: ").append(c.getName()).append("\n");
            for (int j = 0; j < c.getAccountCount(); j++) {
                Account acc = c.getAccounts()[j];
                reportBuilder.append("  -> ").append(acc.getAccountId())
                             .append(" | Balance/Debt: $").append(String.format("%.2f", acc.getBalance()))
                             .append("\n");
            }
        }
        
        System.out.println(reportBuilder.toString());
        Logger.log("INFO", "Report generated.");
    }

    private static void forceGarbageCollection() {
        System.out.println("Initializing memory cleanup protocol...");
        Client temporaryData = new Client("TEMP-999", "ToBeDeleted");
        temporaryData = null;
        System.gc(); 
        System.out.println("Garbage collection explicitly requested.");
        Logger.log("INFO", "System.gc() invoked.");
    }
}