/*  If we call the counter method without synchronization, multiple threads may access and modify the count variable simultaneously,
    leading to inconsistent or incorrect results. By using the synchronized keyword, we ensure that only one thread can execute the 
    increment method at a time, thus maintaining the integrity of the count variable.

    Synchronization is used to control access to shared resources in a multi-threaded environment.
    It works by allowing only one thread to access a synchronized method or block of code at a time, thereby preventing
    thread interference and ensuring data consistency. */

class Counter {
    private int count = 0;

    // Synchronized method to ensure thread safety
    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class SynchronizationDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Create multiple threads that increment the counter
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 10000; i++) {
                counter.increment();
            }
        });

        // Start the threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        t1.join();
        t2.join();

        // Print the final count
        System.out.println("Final count: " + counter.getCount());
    }
}
