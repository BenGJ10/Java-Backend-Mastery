import java.util.concurrent.locks.*;

/* The ReadWriteLockDemo class demonstrates the use of ReadWriteLock to manage concurrent access to a shared resource.
   ReadWriteLock allows multiple threads to read the resource simultaneously while ensuring exclusive access for write operations.
   Only one thread can hold the write lock at a time, preventing other threads from reading or writing during that period.
 */
class SharedResource {
    boolean isAvailable = false;

    public void producer(ReadWriteLock lock){
        lock.writeLock().lock();
        try {
            System.out.println("Producer acquired WRITE lock: " + Thread.currentThread().getName());
            isAvailable = true;
            Thread.sleep(3000);
        }
        catch (Exception e){}
        finally {
            lock.writeLock().unlock();
            System.out.println("Producer released WRITE lock: " + Thread.currentThread().getName());
        }
    }

    public void consumer(ReadWriteLock lock){
        lock.readLock().lock();
        try {
            System.out.println("Consumer acquired READ lock: " + Thread.currentThread().getName());
            System.out.println("Resource available? " + isAvailable);
        } 
        finally {
            lock.readLock().unlock();
            System.out.println("Consumer released READ lock: " + Thread.currentThread().getName());
        }
    }
}


public class ReadWriteLockDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        ReadWriteLock lock = new ReentrantReadWriteLock();   

        Thread thread1 = new Thread(() -> {
            resource.producer(lock);
        }, "Producer-1");
        
        Thread thread2 = new Thread(() -> {
            resource.producer(lock);
        }, "Producer-2");

        Thread thread3 = new Thread(() -> {
            resource.consumer(lock);
        }, "Consumer-1");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
