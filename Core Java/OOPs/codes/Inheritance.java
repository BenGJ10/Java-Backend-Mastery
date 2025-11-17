// Sample Java code demonstrating Inheritance

class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int subtract(int a, int b) {
        return a - b;
    }
}

class AdvancedCalculator extends Calculator { // avoiding redundant code by inheriting from Calculator
    int multiply(int a, int b) {
        return a * b;
    }

    double divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero is not allowed.");
        }
        return (double) a / b;
    }
}

public class Inheritance {
    public static void main(String[] args) {
        AdvancedCalculator advCalc = new AdvancedCalculator();

        int sum = advCalc.add(10, 5);
        int difference = advCalc.subtract(10, 5);
        int product = advCalc.multiply(10, 5);
        double quotient = advCalc.divide(10, 5);

        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);
    }
}
