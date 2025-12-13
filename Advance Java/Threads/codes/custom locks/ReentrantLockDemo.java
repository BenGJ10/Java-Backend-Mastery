import java.util.concurrent.locks.ReentrantLock;

class SharedResource {

    boolean isAvailable = false;
    ReentrantLock lock = new ReentrantLock();

    // Producer method to acquire lock and produce resource
    public void producer() {
        try{
            // Acquire the lock
            lock.lock();
            System.out.println("Lock acquired by: " + Thread.currentThread().getName());
            isAvailable = true;
            Thread.sleep(4000); // Simulate some work
        }
        catch(Exception e){}
        finally {
            // Release the lock
            lock.unlock();
            System.out.println("Lock released by: " + Thread.currentThread().getName());
        }
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        // Create multiple threads to demonstrate lock usage
        Thread t1 = new Thread(() -> resource.producer(), "Thread-1");
        Thread t2 = new Thread(() -> resource.producer(), "Thread-2");

        t1.start();
        t2.start();
    }
}
