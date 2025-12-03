// Practising Java Streams API
// This code demonstrates the use of Java Streams to process collections of data
// It showcases the concept of a Streams pipeline, which consists of a source, intermediate operations, and terminal operations to transform and collect data in a functional style.


import java.util.*;
import java.util.stream.Collectors;

public class StreamMethodsPractice {
    public static void main(String[] args) {
        // Example: Creating a stream from a list of integers
        List<Integer> numbers = Arrays.asList(2, 2, 3, 4, 6, 6, 7, 8, 8, 10, 12, 14, 15, 16, 18, 20);

        // Using Streams to filter, map, and collect data
        List<Double> evenDistinctNumbers = numbers.stream()
                .filter(n -> n % 2 == 0) // Filters even numbers
                .distinct() // Ensures that only distinct even numbers are processed
                .map(n -> Math.pow(n, 3)) // Maps each even number to its cube
                .limit(5) // Limits the result to the first 5 elements
                .collect(Collectors.toList()); // Collects the results into a List

        System.out.println("Even distinct numbers cubed (limited to 5): " + evenDistinctNumbers);

        
    }
}