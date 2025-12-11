/*  Monitor Lock helps in synchronizing access to critical sections of code within methods.
    When a thread enters a synchronized method, it acquires the monitor lock for that object.
    Other threads trying to access synchronized methods on the same object will be blocked until the lock is released.
    This ensures that only one thread can execute a synchronized method at a time, preventing race conditions. */

class MonitorLock{
    public synchronized void initialTask(){
        try{
            System.out.println("Initial Task started by " + Thread.currentThread().getName());
            Thread.sleep(5000); 
            System.out.println("Initial Task completed by " + Thread.currentThread().getName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void subsequentTask(){
        System.out.println("Subsequent Task started by " + Thread.currentThread().getName());
        synchronized(this){
            System.out.println("Subsequent Task executing critical section by " + Thread.currentThread().getName());
        }
        System.out.println("Subsequent Task completed by " + Thread.currentThread().getName());
    }

    public synchronized void finalTask(){
        System.out.println("Final Task started by " + Thread.currentThread().getName());
        System.out.println("Final Task completed by " + Thread.currentThread().getName());
    }
}

public class MonitorLockDemo {
    public static void main(String[] args) {
        MonitorLock monitorlock = new MonitorLock();

        // Creating threads to demonstrate monitor lock behavior
        Thread t1 = new Thread(() -> {monitorlock.initialTask();});
        Thread t2 = new Thread(() -> {monitorlock.subsequentTask();});
        Thread t3 = new Thread(() -> {monitorlock.finalTask();});

        // Start the threads
        t1.start();
        t2.start();
        t3.start();

        // Even though we expect a certain order, due to synchronization, the actual execution order may vary.
    }
}
