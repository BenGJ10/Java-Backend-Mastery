import java.util.concurrent.Semaphore;

/* Semaphore is a synchronization aid that restricts the number of threads that can access a resource.
   We can initialize a Semaphore with a specific number of permits, which determines how many threads can access the resource concurrently.
   In this example, we create a Semaphore with 2 permits, allowing up to 2 threads to access the shared resource at the same time.
*/

class SharedResource{
    boolean isLocked = false;
    Semaphore lock = new Semaphore(2);
    
    public void acquireLock() {
        try {
            lock.acquire();
            System.out.println("Lock acquired by " + Thread.currentThread().getName());
            isLocked = true;
            Thread.sleep(4000); // Simulate some work with the resource
        } 
        catch (Exception e) {}
        finally {
            lock.release();
            System.out.println("Lock released by " + Thread.currentThread().getName());
        }
    }
}

public class SemaphoreLockDemo {
    public static void main(String[] args){
        SharedResource resource = new SharedResource();

        Thread thread1 = new Thread(() -> {
            resource.acquireLock();
        }, "Thread-1");

        Thread thread2 = new Thread(() -> {
            resource.acquireLock();
        }, "Thread-2");

        Thread thread3 = new Thread(() -> {
            resource.acquireLock();
        }, "Thread-3");

        Thread thread4 = new Thread(() -> {
            resource.acquireLock();
        }, "Thread-4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
    
}
