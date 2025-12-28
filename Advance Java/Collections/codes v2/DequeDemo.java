import java.util.ArrayDeque;
import java.util.Deque;

public class DequeDemo {
    public static void main(String[] args) {
        
        Deque<Integer> deque = new ArrayDeque<>();
        // Adding elements to the Deque
        deque.addFirst(10); // addFirst() throws exception if full
        deque.addLast(20);
        deque.offerFirst(5); // offerFirst() returns false if full
        deque.offerLast(25);

        System.out.println("Deque after additions: " + deque);

        // Removing elements from the Deque
        int firstRemoved = deque.removeFirst(); // removeFirst() throws exception if empty
        int lastRemoved = deque.pollLast(); // pollLast() returns null if empty

        System.out.println("Removed from front: " + firstRemoved);
        System.out.println("Removed from end: " + lastRemoved);

        System.out.print("Deque after removals: " + deque);
        
        /*
        Using Deque we can implement both Stack and Queue data structures.
        We can use the methods push() and pop() to use Deque as a Stack.
        Similarly, we can use addLast() and removeFirst() to use Deque as a Queue.
         */

        /*
        Insertion Time Complexity will be O(1) for most of the time.
        But it is amortized O(n) in case of resizing; when the underlying array is full, a new array of double the size is created and all the elements are copied to the new array.
         */
    }
}
