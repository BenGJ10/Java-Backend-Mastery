class SharedResource {
    private boolean itemAvailable = false;

    // Synchronized method to add item to the resource
    public synchronized void addItem() {
        // If an item is already available, wait until it's consumed
        while (itemAvailable) {
            try {
                wait();
            } 
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted while waiting.");
                return;
            }
        }

        itemAvailable = true;
        System.out.println("Item added to the resource by " + Thread.currentThread().getName() + " and notifying others.");
        notifyAll(); // Notify waiting threads that an item is available
    }

    // Synchronized method to consume item from the resource
    public synchronized void consumeItem() {
        System.out.println("Attempting to consume item by " + Thread.currentThread().getName());
        while (!itemAvailable) {
            try {
                System.out.println("No item available for " + Thread.currentThread().getName() + ", waiting...");
                wait(); // Wait until an item is available
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted while waiting.");
                return;
            }
        }

        System.out.println("Item consumed by " + Thread.currentThread().getName());
        itemAvailable = false; // Reset the availability
        notifyAll(); // optional: notify producers waiting to add (if you extend to multiple cycles)
    }
}

public class CompleteSharedResource {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread consumerThread = new Thread(() -> {
            // Consumer will try to consume once
            sharedResource.consumeItem();
        }, "ConsumerThread");

        Thread producerThread = new Thread(() -> {
            try {
                // simulate some production delay
                Thread.sleep(2000);
            } 
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer sleep interrupted.");
                return;
            }
            sharedResource.addItem();
        }, "ProducerThread");

        consumerThread.start();
        producerThread.start();

        // Wait for both threads to finish so main doesn't exit early
        try {
            consumerThread.join();
            producerThread.join();
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted while waiting for workers.");
        }

        System.out.println("Main thread completed execution.");
    }
    // The order of execution is:
    // 1. ConsumerThread starts and finds no item, so it waits.
    // 2. After 2 seconds, ProducerThread adds an item and notifies the waiting consumer.
    // 3. ConsumerThread resumes and consumes the item.
}
