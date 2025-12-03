import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class StreamOperationsSequence {
    public static void main(String[] args){

        List<Integer> numbers = Arrays.asList(2, 7, 4, 10, 5, 3);
        
        // A sequence of stream operations demonstrating filter, map, sorted, and peek
        // We would think that the operations would be executed in the order they are written
        // There's a certain order of execution based on the nature of stream operations (intermediate and terminal)
        // Operations like sorted() are stateful and require processing of the entire stream before proceeding

        Stream<Integer> numberStream = numbers.stream()
            .filter((Integer n) -> n >= 3)
            .peek((Integer n) -> System.out.println("Filtered value: " + n))
            .map((Integer n) -> (n * -1))
            .peek((Integer n) -> System.out.println("Mapped value: " + n))
            .sorted();
            
        List<Integer> result  = numberStream.collect(Collectors.toList());
        System.out.print("Final Result after sorting: " + result);
    }
}
