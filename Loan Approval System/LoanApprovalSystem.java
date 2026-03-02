import java.util.Scanner;

// ======================= ABSTRACT BASE CLASS =======================
/*
 * OOP Concept Used:
 * 1. Abstraction – Common structure and logic are defined here, hiding internal complexity.
 * 2. Inheritance – Subclasses (HousingLoan, VehicleLoan, etc.) inherit from this class.
 * 3. Encapsulation – All loan details are stored as protected members, accessible only to subclasses.
 */
abstract class Loan {
    protected double principal;
    protected double income;
    protected double existingObligations;
    protected double collateralValue;
    protected int creditScore;

    // Constructor - Demonstrates Encapsulation & Constructor Overloading Concept
    public Loan(double principal, double income, double existingObligations, double collateralValue, int creditScore) {
        this.principal = principal;
        this.income = income;
        this.existingObligations = existingObligations;
        this.collateralValue = collateralValue;
        this.creditScore = creditScore;
    }

    // Abstract methods enforce that all subclasses must define their own behavior
    // --> Demonstrates Abstraction & Polymorphism (Dynamic Method Dispatch)
    abstract double getInterestRate();
    abstract int getTenureMonths();
    abstract double getMaxFOIR();
    abstract double getMinCreditScore();
    abstract double getMaxLTV();
    abstract double getMaxEMIToIncomePercent();

    // Calculate EMI using standard EMI formula
    public double calculateEMI() {
        double annualRate = getInterestRate();
        double monthlyRate = annualRate / 12 / 100;
        int months = getTenureMonths();
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) / 
               (Math.pow(1 + monthlyRate, months) - 1);
    }

    // Calculate FOIR (Fixed Obligation to Income Ratio)
    public double calculateFOIR(double proposedEMI) {
        return ((existingObligations + proposedEMI) / income) * 100;
    }

    // Calculate LTV (Loan-to-Value Ratio)
    public double calculateLTV() {
        return (principal / collateralValue) * 100;
    }

    // Calculate EMI as a percentage of income
    public double calculateEMIIncomeRatio(double emi) {
        return (emi / income) * 100;
    }

    // Decision logic for loan eligibility
    // Demonstrates Encapsulation: All data and rules are encapsulated inside this method.
    public void evaluateEligibility() {
        double emi = calculateEMI();
        double foir = calculateFOIR(emi);
        double ltv = calculateLTV();
        double emiRatio = calculateEMIIncomeRatio(emi);

        System.out.printf("\n--- Loan Evaluation Report ---\n");
        System.out.printf("Requested Loan Amount: ₹%.2f\n", principal);
        System.out.printf("Calculated EMI: ₹%.2f\n", emi);
        System.out.printf("EMI as %% of Income: %.2f%%\n", emiRatio);
        System.out.printf("FOIR: %.2f%%\n", foir);
        System.out.printf("LTV: %.2f%%\n", ltv);
        System.out.printf("Credit Score: %d\n", creditScore);
        System.out.println("--------------------------------");

        // ---------- New: Calculate Approval Probability ----------
        String approvalProbability = calculateApprovalProbability(emiRatio, foir, ltv, creditScore);
        System.out.println("Approval Probability: " + approvalProbability);
        System.out.println("--------------------------------");

        // Decision-making logic
        if (creditScore < getMinCreditScore()) {
            System.out.println("Rejected: Credit score below minimum requirement (" + getMinCreditScore() + ").");
        } else if (emiRatio > getMaxEMIToIncomePercent()) {
            System.out.println("Rejected: EMI exceeds " + getMaxEMIToIncomePercent() + "% of income limit.");
        } else if (foir > getMaxFOIR()) {
            System.out.println("Rejected: FOIR exceeds " + getMaxFOIR() + "% threshold.");
        } else if (ltv > getMaxLTV()) {
            System.out.println("Rejected: LTV exceeds " + getMaxLTV() + "% limit.");
        } else {
            System.out.println("Approved: Loan eligible based on all financial parameters.");
        }
    }

    // ---------- New: Approval Probability Calculation ----------
    /*
     * Demonstrates Encapsulation and Polymorphism again.
     * This function estimates approval chance qualitatively using multiple metrics.
     */
    public String calculateApprovalProbability(double emiRatio, double foir, double ltv, int creditScore) {
        int score = 0;

        // Credit Score weight
        if (creditScore >= 800) score += 3;
        else if (creditScore >= 750) score += 2;
        else if (creditScore >= 700) score += 1;

        // EMI Ratio weight
        if (emiRatio <= (getMaxEMIToIncomePercent() * 0.7)) score += 3;
        else if (emiRatio <= getMaxEMIToIncomePercent()) score += 2;

        // FOIR weight
        if (foir <= (getMaxFOIR() * 0.7)) score += 2;
        else if (foir <= getMaxFOIR()) score += 1;

        // LTV weight
        if (ltv <= (getMaxLTV() * 0.7)) score += 2;
        else if (ltv <= getMaxLTV()) score += 1;

        // Total score determines probability band
        if (score >= 8) return "High";
        else if (score >= 5) return "Medium";
        else return "Low";
    }
}

// ======================= SUBCLASSES =======================
/*
 * OOP Concept Used:
 * 1. Inheritance – These subclasses extend Loan and inherit its fields and methods.
 * 2. Polymorphism – Each subclass provides its own implementation for abstract methods.
 * 3. Encapsulation – Specific loan logic is self-contained within each subclass.
 */

// ---- Housing Loan ----
class HousingLoan extends Loan {
    public HousingLoan(double p, double i, double e, double c, int cs) {
        super(p, i, e, c, cs); // Calls parent constructor (Inheritance + Encapsulation)
    }

    @Override double getInterestRate() { return 8.0; }
    @Override int getTenureMonths() { return 20 * 12; }
    @Override double getMaxFOIR() { return 50.0; }
    @Override double getMinCreditScore() { return 750.0; }
    @Override double getMaxLTV() { return 80.0; }
    @Override double getMaxEMIToIncomePercent() { return 35.0; }
}

// ---- Vehicle Loan ----
class VehicleLoan extends Loan {
    public VehicleLoan(double p, double i, double e, double c, int cs) {
        super(p, i, e, c, cs);
    }

    @Override double getInterestRate() { return 9.5; }
    @Override int getTenureMonths() { return 7 * 12; }
    @Override double getMaxFOIR() { return 45.0; }
    @Override double getMinCreditScore() { return 750.0; }
    @Override double getMaxLTV() { return 85.0; }
    @Override double getMaxEMIToIncomePercent() { return 30.0; }
}

// ---- Secured Personal Loan ----
class SecuredPersonalLoan extends Loan {
    public SecuredPersonalLoan(double p, double i, double e, double c, int cs) {
        super(p, i, e, c, cs);
    }

    @Override double getInterestRate() { return 10.5; }
    @Override int getTenureMonths() { return 5 * 12; }
    @Override double getMaxFOIR() { return 40.0; }
    @Override double getMinCreditScore() { return 700.0; }
    @Override double getMaxLTV() { return 75.0; }
    @Override double getMaxEMIToIncomePercent() { return 25.0; }
}

// ======================= MAIN DRIVER CLASS =======================
/*
 * OOP Concept Used:
 * 1. Polymorphism – A single reference (Loan loan) can hold any loan type object.
 * 2. Encapsulation – All data is read from user input, not accessed directly from outside.
 */
public class LoanApprovalSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select Loan Type: ");
        System.out.println("1. Housing Loan");
        System.out.println("2. Vehicle Loan");
        System.out.println("3. Secured Personal Loan");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        System.out.print("Enter Principal Loan Amount (₹): ");
        double principal = sc.nextDouble();
        System.out.print("Enter Net Monthly Income (₹): ");
        double income = sc.nextDouble();
        System.out.print("Enter Existing Monthly Obligations (₹): ");
        double obligations = sc.nextDouble();
        System.out.print("Enter Collateral Value (₹): ");
        double collateral = sc.nextDouble();
        System.out.print("Enter Credit Score: ");
        int creditScore = sc.nextInt();

        // Input Validation – Defensive Programming
        if (principal <= 0 || income <= 0 || collateral <= 0) {
            System.out.println("Invalid input values! Please enter positive numbers.");
            sc.close();
            return;
        }

        // Demonstrates Polymorphism: Reference variable of type Loan can hold any subclass object
        Loan loan = null;

        switch (choice) {
            case 1 -> loan = new HousingLoan(principal, income, obligations, collateral, creditScore);
            case 2 -> loan = new VehicleLoan(principal, income, obligations, collateral, creditScore);
            case 3 -> loan = new SecuredPersonalLoan(principal, income, obligations, collateral, creditScore);
            default -> {
                System.out.println("Invalid choice! Exiting.");
                sc.close();
                return;
            }
        }

        // Calls overridden methods dynamically – Runtime Polymorphism
        loan.evaluateEligibility();

        sc.close();
    }
}