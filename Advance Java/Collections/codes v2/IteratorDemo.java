import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDemo {
        public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        
        /* 
        Iterator interface is used to traverse the collection, it has three main methods:
        hasNext(): returns true if there are more elements to iterate over
        next(): returns the next element in the iteration
        remove(): removes the last element returned by the iterator from the underlying collection 
        */

        // We can use Iterator to traverse the collection
        Iterator<Integer> iterator = numbers.iterator();
        System.out.println("Iterating over the collection:");
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            System.out.println("Number: " + number);
            if(number == 20) {
                iterator.remove(); // Remove the element 20 from the collection
            }
        }

        System.out.println("Collection after removal:");
        for (Integer number : numbers) {
            System.out.println("Number: " + number);
        }

        System.out.println("Printing using forEach method:");
        numbers.forEach((Integer number) -> System.out.println("Number: " + number));

    }
}