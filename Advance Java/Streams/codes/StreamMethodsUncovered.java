
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamMethodsUncovered {
    public static void main(String[] args) {
        
        // Example: Creating a stream from a list of integers
        List<Integer> numbers = Arrays.asList(2, 2, 3, 4, 6, 6, 7, 8, 8, 10, 12, 14, 15, 16, 18, 20);

        // Predicate is a functional interface that can be used to filter elements
        Predicate<Integer> pred = new Predicate<Integer>() {
            @Override
            public boolean test(Integer n) {
                return n % 2 == 0; // Predicate to filter even numbers
            }
        };
        
        // Function is a functional interface that can be used to transform elements
        Function<Integer, Integer> func = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer n) {
                return n * n; // Function to square the number
            }
        };

        int collection = numbers.stream()
                .filter(pred) // Can replace predicate with a lambda expression: n -> n % 2 == 0
                .map(func) // Can replace function with a lambda expression: n -> n * n
                .reduce(0, (sum, n) -> sum + n); // Terminal Operation: reduce to sum

        System.out.println("Sum of squares of even numbers: " + collection);
    }
}
