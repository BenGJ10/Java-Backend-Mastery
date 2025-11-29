public class ThrowAndThrowsDemo {

    // Method that uses `throws` to declare an exception
    static int divide(int dividend, int divisor) throws ArithmeticException {
        if (divisor == 0) {
            // Using `throw` to manually throw exception
            // ❗️❗️ If we don't throw an execption here, JVM will throw ArithmeticException automatically and the program will terminate
            throw new ArithmeticException("Divisor cannot be zero!");
        }
        return dividend / divisor;
    }

    public static void main(String[] args) {
        int a = 50;
        int b = 0;
        
        // If we don't handle the exception here, it will propagate to JVM and terminate the program
        try { 
            // Method that may throw exception
            int result = divide(a, b); 
            System.out.println("Result: " + result);
        }
        catch (ArithmeticException e) {
            System.out.println("Exception caught: " + e.getMessage() + e.getStackTrace());
        }
        finally {
            System.out.println("Program executed successfully.");
        }

        // Just because we are having throws in method signature, we still need to handle the exception
        // Without handling, the program will terminate abruptly
    }
}
