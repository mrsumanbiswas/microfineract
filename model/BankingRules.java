// this will hold the nested interfaces (e.g., InterestBearing, Penalizable)
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