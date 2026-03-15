package LoanApprovalSystem;
// ✅ Inheritance + Polymorphism: Overrides base rules for housing loan
public class HousingLoan extends Loan {
    public HousingLoan(String name, int age, String idProof, String employment, String loanType, double principal, double income, double obligations, double collateral, int creditScore) {
        super(name, age, idProof, employment, loanType, principal, income, obligations, collateral, creditScore);
    }
    @Override public double getInterestRate() { return 8.0; }
    @Override public int getTenureMonths() { return 20 * 12; }
    @Override public double getMaxFOIR() { return 50.0; }
    @Override public double getMinCreditScore() { return 750; }
    @Override public double getMaxLTV() { return 80.0; }
    @Override public double getMaxEMIToIncomePercent() { return 35.0; }
}