// Parallel Streams can be used to leverage multi-core processors for parallel processing of data

import java.util.*;
public class ParallelStreamsDemo {
    public static void main(String[] args){
        int size = 10_000; // A new way of writing numbers with underscores for better readability
        
        List<Integer> numbers = new ArrayList<>(size);
        Random random = new Random();
        
        for(int i=0; i<size; i++){
            numbers.add(random.nextInt(100)); // Adding random integers between 0 and 99
        }
        
        // Utilising Sequential Stream to process data
        long startSequential = System.currentTimeMillis();
        int sumSequential = numbers.stream()
                                .map(i -> {
                                    try {Thread.sleep(1);} catch (Exception e){}
                                    return i * 2; })
                                .mapToInt(i -> i)
                                .sum();
        long endSequential = System.currentTimeMillis();
        

        // Utilising Parallel Stream to process the same data in parallel
        long startParallel = System.currentTimeMillis();
        int sumParallel = numbers.parallelStream()
                                .map(i -> {
                                    try {Thread.sleep(1);} catch (Exception e){}
                                    return i * 2; })
                                .mapToInt(i -> i)
                                .sum();
        long endParallel = System.currentTimeMillis();

        System.out.println("Sum using Sequential Stream: " + sumSequential + ", Time taken: " + (endSequential - startSequential) + " ms");
        System.out.println("Sum using Parallel Stream: " + sumParallel + ", Time taken: " + (endParallel - startParallel) + " ms");
    }
}
