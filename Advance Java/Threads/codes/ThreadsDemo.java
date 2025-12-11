// To implement multithreading in Java, we can create classes that extend the Thread class.
// Each class overrides the run() method to define the code that will be executed in the thread.

class AthleteA extends Thread {
    
    // run() is the entry point for the thread. It contains the code that will be executed when the thread starts.
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Athlete A is running lap " + (i + 1));
            try {
                Thread.sleep(500); // Simulate time taken to run a lap
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class AthleteB extends Thread {
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Athlete B is running lap " + (i + 1));
            try {
                Thread.sleep(700); // Simulate time taken to run a lap
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ThreadsDemo {
    public static void main(String[] args) {
        
        // As we are extending Thread, we can create instances of our thread classes directly. No need for Runnable interface here.
        AthleteA athleteA = new AthleteA();
        AthleteB athleteB = new AthleteB();

        // Start both threads
        athleteA.start();
        athleteB.start();
        
        // We can see the output from both threads interleaved, demonstrating concurrent execution.
        System.out.println("Both athletes have finished their races!"); 
    }
}
