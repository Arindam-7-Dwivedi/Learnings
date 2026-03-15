package LoanApprovalSystem;
import java.util.*;
// Main driver class showing full OOP and validation
public class LoanApproval {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("========== Loan Approval System ==========");
        while (true) {
            try {
                // clear any newline left from previous loop before reading name
                sc.nextLine(); 

                String name = getValidName(sc);
                int age = getPositiveWholeInt(sc, "Enter age: ");
                String idProof = getValidPanStatus(sc);
                String employment = getValidEmploymentType(sc);

                System.out.println("\nSelect Loan Type:");
                System.out.println("1. Housing Loan");
                System.out.println("2. Vehicle Loan");
                System.out.println("3. Personal Loan");
                int choice = getValidInt(sc, "Enter choice (1-3): ", 1, 3);

                double principal = getValidDouble(sc, "Enter loan amount (Principal): ");
                double income = getValidDouble(sc, "Enter monthly income: ");
                double obligations = getValidDouble(sc, "Enter total monthly obligations (other EMIs): ");
                double collateral = getValidDouble(sc, "Enter collateral value: ");
                int creditScore = getPositiveWholeInt(sc, "Enter credit score: ");

                Loan loan;
                switch (choice) {
                    case 1 -> loan = new HousingLoan(name, age, idProof, employment, "Housing Loan",
                            principal, income, obligations, collateral, creditScore);
                    case 2 -> loan = new VehicleLoan(name, age, idProof, employment, "Vehicle Loan",
                            principal, income, obligations, collateral, creditScore);
                    case 3 -> loan = new SecuredPersonalLoan(name, age, idProof, employment, "Personal Loan",
                            principal, income, obligations, collateral, creditScore);
                    default -> throw new IllegalArgumentException("Invalid loan type");
                }
                System.out.println("\n" + loan.evaluateEligibility());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.print("\nDo you want to evaluate another loan? (y/n): ");
            String cont = sc.next().trim();
            if (!cont.equalsIgnoreCase("y")) break;
        }
        System.out.println("\nThank you for using the Loan Approval System!");
        sc.close();
    }
    
    // ------------------ VALIDATION METHODS ------------------
    // Name validation – allows only letters and spaces
    private static String getValidName(Scanner sc) {
        while (true) {
            System.out.print("Enter applicant name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
            } else if (name.matches("[A-Za-z ]+")) {
                return name;
            } else {
                System.out.println("Invalid name! Please use letters and spaces only.");
            }
        }
    }

    // Integer input validation with optional range
    private static int getValidInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                if (val >= min && val <= max) return val;
                System.out.println("Value must be between " + min + " and " + max + ".");
            } else {
                System.out.println("Invalid input! Please enter a whole number.");
                sc.next(); // discard invalid token
            }
        }
    }

    // Positive integer validation (for age, credit score, etc.)
    private static int getPositiveWholeInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int val = sc.nextInt();
                if (val > 0) return val;
                else System.out.println("Value must be a positive whole number.");
            } 
            else {
                System.out.println("Invalid input! Please enter a whole number.");
                sc.next(); // discard invalid token
            }
        }
    }

    // Floating-point number validation
    private static double getValidDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            } else {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.next(); // discard invalid token
            }
        }
    }

    // PAN validation
    private static String getValidPanStatus(Scanner sc) {
        while (true) {
            System.out.println("\nPAN Card Verification:");
            System.out.println("1. Yes, I have PAN");
            System.out.println("2. No, I don't have PAN");
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: return "PAN";
                    case 2: return "No";
                    default: System.out.println("Invalid input! Choose 1 or 2.");
                }
            } else {
                System.out.println("Invalid input! Please enter a number (1 or 2).");
                sc.next();
            }
        }
    }

    // Employment type validation
    private static String getValidEmploymentType(Scanner sc) {
        while (true) {
            System.out.println("\nSelect Employment Type:");
            System.out.println("1. Salaried");
            System.out.println("2. Self-Employed");
            System.out.print("Enter your choice: ");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: return "Salaried";
                    case 2: return "SelfEmployed";
                    default: System.out.println("Invalid input! Choose 1 or 2.");
                }
            }
            else {
                System.out.println("Invalid input! Please enter a number (1 or 2).");
                sc.next();
            }
        }
    }
}