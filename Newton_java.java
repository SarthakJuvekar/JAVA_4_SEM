public class Newton_java {
    /**
     * Compute the square root of x using Newton's method (a.k.a. Heron's method).
     * @param x value to compute sqrt for (must be >= 0)
     * @param tol stopping tolerance (absolute change)
     * @param maxIter maximum iterations to perform
     * @return approximate sqrt(x)
     * @throws IllegalArgumentException if x < 0
     **/
    public static double sqrtNewton(double x, double tol, int maxIter) {
        if (x < 0) throw new IllegalArgumentException("Cannot compute square root of negative number: " + x);
        if (x == 0 || x == 1) return x;

        double guess = x > 1.0 ? x / 2.0 : 1.0; // a reasonable initial guess
        for (int i = 0; i < maxIter; i++) {
            double next = 0.5 * (guess + x / guess);
            if (Math.abs(next - guess) <= tol) return next;
            guess = next;
        }
        return guess; // return last approximation if not converged within maxIter
    }

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                double x = Double.parseDouble(args[0]);
                double tol = args.length >= 2 ? Double.parseDouble(args[1]) : 1e-12;
                int maxIter = args.length >= 3 ? Integer.parseInt(args[2]) : 1000;

                double result = sqrtNewton(x, tol, maxIter);
                System.out.printf("Newton sqrt(%.12g) = %.12g%n", x, result);
                System.out.printf("Math.sqrt(%.12g)   = %.12g%n", x, Math.sqrt(x));
            } catch (NumberFormatException e) {
                System.err.println("Invalid numeric argument: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } else {
            // Demo with a few sample values when no arguments provided
            double[] samples = {2.0, 3.0, 100.0, 0.25, 0.0, 1.0};
            for (double s : samples) {
                double r = sqrtNewton(s,1e-12, 1000);
                System.out.printf("x=%.6g -> \n Newton: %.12g, Math.sqrt: %.12g%n", s, r, Math.sqrt(s));
            }
        }
    }
}