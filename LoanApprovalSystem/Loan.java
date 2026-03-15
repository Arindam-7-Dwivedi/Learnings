package LoanApprovalSystem;

// OOP Concept: Abstraction (abstract base class)
// OOP Concept: Encapsulation (protected data, public methods)
public abstract class Loan {
    protected String name, idProof, employment, loanType;
    protected int age, creditScore;
    protected double principal, income, obligations, collateral;
    public Loan(String name, int age, String idProof, String employment, String loanType,    double principal, double income, double obligations, double collateral, int creditScore) {
        this.name = name;
        this.age = age;
        this.idProof = idProof;
        this.employment = employment;
        this.loanType = loanType;
        this.principal = principal;
        this.income = income;
        this.obligations = obligations;
        this.collateral = collateral;
        this.creditScore = creditScore;
    }
    // Abstract methods — must be defined by subclasses
    public abstract double getInterestRate();
    public abstract int getTenureMonths();
    public abstract double getMaxFOIR();
    public abstract double getMinCreditScore();
    public abstract double getMaxLTV();
    public abstract double getMaxEMIToIncomePercent();
    // Encapsulated reusable methods
    public double calculateEMI() {
        double monthlyRate = getInterestRate() / 12 / 100;
        int months = getTenureMonths();
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
    }
    public double calculateFOIR(double emi) {
        return ((obligations + emi) / income) * 100;
    }
    public double calculateLTV() {
        return (principal / collateral) * 100;
    }
    public double calculateEMIIncomeRatio(double emi) {
        return (emi / income) * 100;
    }
    // Abstracted logic — eligibility rules hidden inside class
    public String evaluateEligibility() {
        StringBuilder sb = new StringBuilder();
        // --- Basic checks
        if (age < 21 || age > 55)
            return "Sorry " + name + ", age not eligible for loan (21 - 55 years).";
        if (idProof.equalsIgnoreCase("No"))
            return "Aadhaar/PAN required for loan approval.";
        if (employment.equalsIgnoreCase("SelfEmployed"))
            sb.append("Self-employed applicants may face stricter evaluation.\n");
        double emi = calculateEMI();
        double foir = calculateFOIR(emi);
        double ltv = calculateLTV();
        double emiRatio = calculateEMIIncomeRatio(emi);
        sb.append(String.format("\n--- Loan Evaluation Report ---\n"+name+"\n"));
        sb.append(String.format("Requested Loan Amount: Rs %.2f\n", principal));
        sb.append(String.format("Calculated EMI: Rs %.2f\n", emi));
        sb.append(String.format("FOIR: %.2f%%\n", foir));
        sb.append(String.format("LTV: %.2f%%\n", ltv));
        sb.append(String.format("Credit Score: %d\n", creditScore));
        sb.append("\nApproval Probability: ").append(calculateApprovalProbability(emiRatio, foir, ltv, creditScore)).append("\n");
        if (creditScore < getMinCreditScore())
            sb.append("Rejected: Credit score below minimum.\n");
        else if (emiRatio > getMaxEMIToIncomePercent())
            sb.append("Rejected: EMI exceeds " + getMaxEMIToIncomePercent() + "% of income.\n");
        else if (foir > getMaxFOIR())
            sb.append("Rejected: FOIR exceeds " + getMaxFOIR() + "%.\n");
        else if (ltv > getMaxLTV())
            sb.append("Rejected: LTV exceeds " + getMaxLTV() + "%.\n");
        else
            sb.append("Approved: Loan eligible based on all parameters.\n");

        return sb.toString();
    }
    private String calculateApprovalProbability(double emiRatio, double foir, double ltv, int creditScore) {
        int score = 0;
        if (creditScore >= 800) score += 3;
        else if (creditScore >= 750) score += 2;
        else if (creditScore >= 700) score += 1;
        if (emiRatio <= getMaxEMIToIncomePercent() * 0.7) score += 3;
        if (foir <= getMaxFOIR() * 0.7) score += 2;
        if (ltv <= getMaxLTV() * 0.7) score += 2;
        if (score >= 8) return "High";
        else if (score >= 5) return "Medium";
        else return "Low";
    }
}