// A basic pipeline structure for Java Streams

/*  The arrival of Streams in Java 8 revolutionized the way we handle collections of data.
    We used to rely heavily on loops and conditional statements to process data, which often led to verbose and less readable code.
    Streams introduced a functional approach to processing sequences of elements, allowing for more concise and expressive code
    through a series of operations known as a pipeline.

    There are mainly three components in a Streams pipeline:
    1. Source: The source is the data structure or collection from which the stream is created.
       It can be a Collection (like List, Set), an array, or even I/O channels.
    2. Intermediate Operations: These operations transform the stream into another stream.
       They are lazy, meaning they do not process the data until a terminal operation is invoked.
       Common intermediate operations include filter(), map(), and sorted().
    3. Terminal Operations: These operations produce a result or a side effect and mark the end of the stream pipeline.
       They trigger the processing of the data. Examples include forEach(), collect(), and reduce().
*/

import java.util.*;
import java.util.stream.Collectors;

public class StreamsPipelineDemo {
    public static void main(String[] args) {
        // Example: Creating a stream from a list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Stream Pipeline
        List<Integer> evenSquares = numbers.stream() // Source
            .filter(n -> n % 2 == 0)                // Intermediate Operation: filter even numbers
            .map(n -> n * n)                        // Intermediate Operation: square the numbers
            .collect(Collectors.toList());          // Terminal Operation: collect results into a list

        // forEach() is a terminal operation that can be used to print the results
        System.out.print("Squared even numbers: ");
        evenSquares.forEach(n -> System.out.print(n + " ")); // Print each squared even number

        // Another example showcasing the sum of squares of odd numbers
        int sumOfOddSquares = numbers.stream() // Source
            .filter(n -> n % 2 != 0)              // Intermediate Operation: filter odd numbers
            .map(n -> n * n)                       // Intermediate Operation: square the numbers
            .reduce(0, (sum, n) -> sum + n); // Terminal Operation: reduce to sum

        System.out.println("\nSum of squares of odd numbers: " + sumOfOddSquares); // Print the sum
    }        
}
