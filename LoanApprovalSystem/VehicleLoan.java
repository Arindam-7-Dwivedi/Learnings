package LoanApprovalSystem;

public class VehicleLoan extends Loan {
    public VehicleLoan(String name, int age, String idProof, String employment, String loanType,
                       double principal, double income, double obligations, double collateral, int creditScore) {
        super(name, age, idProof, employment, loanType, principal, income, obligations, collateral, creditScore);
    }

    @Override public double getInterestRate() { return 9.5; }
    @Override public int getTenureMonths() { return 7 * 12; }
    @Override public double getMaxFOIR() { return 45.0; }
    @Override public double getMinCreditScore() { return 750; }
    @Override public double getMaxLTV() { return 85.0; }
    @Override public double getMaxEMIToIncomePercent() { return 30.0; }
}