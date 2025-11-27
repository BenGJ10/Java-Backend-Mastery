// Demonstration of thread priorities and sleep method in Java

class AtheleteRunning implements Runnable{
    private String name;
    private int priority;
    
    // Constructor to initialize athlete name and priority
    public AtheleteRunning(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
    
    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        for (int i = 0; i < 5; i++) {
            System.out.println(name + " is running lap " + (i + 1) + " with priority " + Thread.currentThread().getPriority());
            try {
                // Need to use try catch block to handle InterruptedException
                Thread.sleep(1000); // Simulate time taken to run a lap
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class PriorityAndSleepDemo {
    public static void main(String[] args) {
        Thread athlete1 = new Thread(new AtheleteRunning("Athlete 1", Thread.MIN_PRIORITY));
        Thread athlete2 = new Thread(new AtheleteRunning("Athlete 2", Thread.NORM_PRIORITY));
        Thread athlete3 = new Thread(new AtheleteRunning("Athlete 3", Thread.MAX_PRIORITY));
        
        athlete1.start();
        athlete2.start();
        athlete3.start();
        
        try {
            athlete1.join();
            athlete2.join();
            athlete3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("All athletes have finished their races!");  
    }
}
