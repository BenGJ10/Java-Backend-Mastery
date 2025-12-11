// Implement the Producer-Consumer Problem using Threads in Java

/* Two threads: one producer and one consumer.
   The producer will produce an item (simulated by a sleep) and notify the consumer.
   The consumer will wait for the item to be produced before consuming it.
   The problem is to make sure that the consumer waits if there is no item produced yet,
   and the producer notifies the consumer once the item is produced. 
 */

import java.util.*;

class SharedResource{
    private Queue<Integer> sharedBuffer;
    private int bufferSize;
    
    // Constructor to initialize buffer and size
    public SharedResource(int size){
        this.sharedBuffer = new LinkedList<>();
        this.bufferSize = size;
    }

    public synchronized void addItem(int item) throws Exception{
        // If the Buffer is full, wait for the consumer to consume items
        while(sharedBuffer.size() == bufferSize){
            System.out.println("Buffer is full, Producer is waiting...");
            wait();
        }
        sharedBuffer.add(item);
        System.out.println("Produced: " + item);
        notifyAll(); // Notify the consumer that an item is available
    }

    public synchronized int consumeItem() throws Exception{
        // If the Buffer is empty, wait for the producer to produce items
        while(sharedBuffer.isEmpty()){
            System.out.println("Buffer is empty, Consumer is waiting...");
            wait();
        }
        int item = sharedBuffer.poll();
        System.out.println("Consumed: " + item);
        notifyAll(); // Notify the producer that space is available
        return item;
    }
}
public class ProducerConsumerProblem {
    public static void main(String[] args){
        SharedResource sharedResource = new SharedResource(3); // Buffer size of 3

        Thread producerThread = new Thread(() -> {
            try{
                for(int i = 1; i <= 6; i++){
                    sharedResource.addItem(i);
                }
            }
            catch(Exception e){
                System.out.println("Producer interrupted.");
            }
        }, "ProducerThread");

        Thread consumerThread = new Thread(() -> {
            try{
                for(int i = 1; i <= 6; i++){
                    sharedResource.consumeItem();
                }
            }
            catch(Exception e){
                System.out.println("Consumer interrupted.");
            }
        }, "ConsumerThread");

        producerThread.start();
        consumerThread.start();

        System.out.println("Main thread started Producer and Consumer threads.");
    }
}
