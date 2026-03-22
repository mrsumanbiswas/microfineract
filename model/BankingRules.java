// nested interfaces
package model;

public interface BankingRules {

    public interface InterestBearing {
        void calculateInterest();
    }

    public interface Penalizable {
        void applyPenalty();
    }
}

interface InternalAuditable {
    void runAudit();
}