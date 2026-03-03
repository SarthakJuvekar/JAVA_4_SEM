import java.util.Scanner;

public class Newton_java {
    /**
     * Inner class to hold square root result and iteration count
     **/
    public static class SqrtResult {
        public double result;
        public int iterations;
        
        public SqrtResult(double result, int iterations) {
            this.result = result;
            this.iterations = iterations;
        }
    }
    
    /**
     * Compute the square root of x using Newton's method (a.k.a. Heron's method).
     * @param x value to compute sqrt for (must be >= 0)
     * @param tol stopping tolerance (absolute change)
     * @param maxIter maximum iterations to perform
     * @return SqrtResult containing approximate sqrt(x) and iteration count
     * @throws IllegalArgumentException if x < 0
     **/
    public static SqrtResult sqrtNewton(double x, double tol, int maxIter) {
        if (x < 0) throw new IllegalArgumentException("Cannot compute square root of negative number: " + x);
        if (x == 0 || x == 1) return new SqrtResult(x, 0);

        double guess = x > 1.0 ? x / 2.0 : 1.0; // a reasonable initial guess
        for (int i = 0; i < maxIter; i++) {
            double next = 0.5 * (guess + x / guess);
            if (Math.abs(next - guess) <= tol) return new SqrtResult(next, i + 1);
            guess = next;
        }
        return new SqrtResult(guess, maxIter); // return last approximation if not converged within maxIter
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("========== Newton's Square Root Calculator ==========");
        System.out.println("Choose an option:");
        System.out.println("1. Use predefined sample numbers");
        System.out.println("2. Enter a custom number");
        System.out.print("Enter your choice (1 or 2): ");
        
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input! Defaulting to sample numbers.");
            choice = 1;
        }
        
        if (choice == 2) {
            // Custom number input - loop until user stops
            double tol = 1e-12;
            int maxIter = 1000;
            boolean continueInput = true;
            
            System.out.println("\n========== Custom Number Input ==========");
            System.out.println("Enter numbers one by one. Type 'stop' or 'exit' to quit.");
            
            while (continueInput) {
                System.out.print("\nEnter a number (or 'stop' to exit): ");
                String input = scanner.nextLine().trim();
                
                // Check if user wants to stop
                if (input.equalsIgnoreCase("stop") || input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting custom input mode. Thank you!");
                    continueInput = false;
                } else if (input.isEmpty()) {
                    System.out.println("Please enter a valid number or type 'stop' to exit.");
                } else {
                    try {
                        double customNumber = Double.parseDouble(input);
                        SqrtResult result = sqrtNewton(customNumber, tol, maxIter);
                        System.out.printf("Newton's method sqrt(%.12g) = %.12g%n", customNumber, result.result);
                        System.out.printf("Math.sqrt(%.12g)            = %.12g%n", customNumber, Math.sqrt(customNumber));
                        System.out.printf("Iterations required: %d%n", result.iterations);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number! Please enter a valid number or 'stop' to exit.");
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        } else {
            // Demo with sample values
            double[] samples = {2.0, 3.0, 100.0, 0.25, 0.0, 1.0};
            System.out.println("\n========== Calculating Sample Numbers ==========");
            for (double s : samples) {
                SqrtResult result = sqrtNewton(s, 1e-12, 1000);
                System.out.printf("x=%.6g -> Newton: %.12g, Math.sqrt: %.12g, Iterations: %d%n", s, result.result, Math.sqrt(s), result.iterations);
            }
        }
        
        scanner.close();
    }
}