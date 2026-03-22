// Custom exception hierarchy here
// InvalidTransactionException
// InsufficientBalanceError

package exception;

public class BankExceptions {

    public static class BankingException extends Exception {
        public BankingException(String message) {
            super(message);
        }
    }

    public static class TransactionException extends RuntimeException {
        public TransactionException(String message) {
            super(message);
        }
    }
}