package LoanApprovalSystem;

public class SecuredPersonalLoan extends Loan {
    public SecuredPersonalLoan(String name, int age, String idProof, String employment, String loanType,
                               double principal, double income, double obligations, double collateral, int creditScore) {
        super(name, age, idProof, employment, loanType, principal, income, obligations, collateral, creditScore);
    }

    @Override public double getInterestRate() { return 10.5; }
    @Override public int getTenureMonths() { return 5 * 12; }
    @Override public double getMaxFOIR() { return 40.0; }
    @Override public double getMinCreditScore() { return 700; }
    @Override public double getMaxLTV() { return 75.0; }
    @Override public double getMaxEMIToIncomePercent() { return 25.0; }
}
