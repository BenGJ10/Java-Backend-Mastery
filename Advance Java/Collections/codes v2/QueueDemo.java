import java.util.Queue;

public class QueueDemo {
    
    public static void main(String[] args) {
        Queue<Integer> queue = new java.util.LinkedList<>();

        // Adding elements to the queue
        queue.add(10);
        queue.add(20);
        queue.add(30);

        // We can also use offer() method to add elements, which will not throw an exception if the queue is full
        queue.offer(40);
        queue.offer(50);

        // Displaying the queue
        System.out.println("Queue: " + queue);

        // Removing elements from the queue
        System.out.println("Removed element: " + queue.remove()); // removes 10
        System.out.println("Removed element: " + queue.poll());   // poll() also removes, but it won't throw an exception if the queue is empty

        // Displaying the first element without removing it
        System.out.println("Head of the queue using element(): " + queue.element()); // throws exception if the queue is empty
        System.out.println("Head of the queue using peek(): " + queue.peek()); // returns null if the queue is empty
        
    }
}
