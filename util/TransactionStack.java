// dynamic stack
// inner class for the nodes or the ierator && nested classes and access control

package util;

public class TransactionStack {
    private String[] transactions;
    private int top;
    private static final int INITIAL_CAPACITY = 5;

    public TransactionStack() {
        transactions = new String[INITIAL_CAPACITY];
        top = -1;
    }

    public void push(String transaction) {
        if (top == transactions.length - 1) {
            expandCapacity();
        }
        transactions[++top] = transaction;
    }

    private void expandCapacity() {
        String[] newArray = new String[transactions.length * 2];
        System.arraycopy(transactions, 0, newArray, 0, transactions.length);
        transactions = newArray; // odl array mem freed by gc
        Logger.log("INFO", "Transaction stack dynamically resized to " + transactions.length);
    }

    public StackIterator getIterator() {
        return new StackIterator();
    }

    public class StackIterator {
        private int currentIndex = top;

        public boolean hasNext() {
            return currentIndex >= 0;
        }

        public String getNext() {
            if (!hasNext()) return null;
            return transactions[currentIndex--];
        }
    }
}