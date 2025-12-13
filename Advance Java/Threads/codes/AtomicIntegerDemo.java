/* Compare and Swap is a lock-free synchronization primitive used in concurrent programming to achieve atomicity. 
 It allows multiple threads to safely update a shared variable without using traditional locking mechanisms, which can lead to contention and performance bottlenecks.
 The basic idea behind CAS is to compare the current value of a variable with an expected value and, if they match, 
 update the variable to a new value in a single atomic operation. This operation is typically provided by hardware-level atomic instructions, 
 making it efficient and fast.

 AtomicInteger is a class in the java.util.concurrent.atomic package that provides a way to work with integer values atomically. */

import java.util.concurrent.atomic.AtomicInteger;

class Counter{
    // We can use AtomicInteger for read, write and update operations atomically
    // In the background AtomicInteger uses CAS operations and a volatile variable to ensure visibility across threads
    AtomicInteger counter = new AtomicInteger(0);

    public void increment(){
        counter.getAndIncrement(); // atomically increments by 1
    }

    public int getCount(){
        return counter.get(); // returns the current value
    }
}

public class AtomicIntegerDemo {
    public static void main(String[] args) {
        Counter counter = new Counter();

        // Creating 1000 threads to increment the counter
        Thread[] threads = new Thread[1000];
        for(int i=0; i<1000; i++){
            threads[i] = new Thread(() -> {
                counter.increment();
            });
            threads[i].start();
        }

        // Waiting for all threads to finish
        for(int i=0; i<1000; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Displaying the final count
        System.out.println("Final Count: " + counter.getCount());
    }    
}
