// Lambda Expressions work only with Functional Interfaces

@FunctionalInterface
interface Lambda{
    void display();
}

@FunctionalInterface
interface Result{
    int calculate(int a, int b);
}

public class LambdaDemo{
    public static void main(String[] args) {
        Lambda lam = () -> System.out.println("Hello from Lambda Expression");
        lam.display();

        // Lambda expressions with return type
        Result res = (a, b) -> a + b; // no need to specify return
        int sum = res.calculate(10, 20);
        System.out.println("Sum: " + sum);
    }
}