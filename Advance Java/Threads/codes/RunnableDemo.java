// We use Runnable interface to implement threads in Java
// It is the preferred way to implement threads as Java supports only single inheritance
// By implementing Runnable interface, we can extend any other class as well

class RunnableA implements Runnable {
    public void run() {
        System.out.println("Thread A is running");
    }
}

public class RunnableDemo {
    public static void main(String[] args) {
        
        // We need to create Runnable objects first then pass them to Thread constructor
        RunnableA runnableA = new RunnableA(); // We create a runnable object
        
        // Passing runnable object to Thread constructor
        Thread threadA = new Thread(runnableA); // It will internally call run() method of RunnableA class
        
        // Creating thread using lambda expression
        Runnable runnableB = () -> {
            System.out.println("Thread B is running");
        };
        Thread threadB = new Thread(runnableB);
        
        // Starting the threads
        threadA.start();
        threadB.start();
    }    
}
