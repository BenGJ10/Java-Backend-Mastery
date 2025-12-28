import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        // Creating a PriorityQueue
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        // Adding elements to the PriorityQueue, naturally it acts like a min-heap
        priorityQueue.add(8);
        priorityQueue.add(10);
        priorityQueue.add(20);
        priorityQueue.add(5);
        priorityQueue.offer(15);

        // Displaying the PriorityQueue
        System.out.print("PriorityQueue elements (in heap order): ");
        priorityQueue.forEach((Integer value) -> System.out.print(value + " "));

        // Removing elements from the PriorityQueue
        System.out.println("\nRemoved element from top: " + priorityQueue.poll()); // removes the head (smallest element)
    
        // Displaying the head of the PriorityQueue without removing it
        System.out.println("Head of the PriorityQueue using peek(): " + priorityQueue.peek()); // returns null if the queue is empty

        // Final state of the PriorityQueue
        System.out.println("Final PriorityQueue: " + priorityQueue);
    }   
}
