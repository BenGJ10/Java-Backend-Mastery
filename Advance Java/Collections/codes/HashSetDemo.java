import java.util.Set;
import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        // Create a HashSet to store unique elements
        Set<Integer> numbers = new HashSet<>();
        
        // Add elements to the set
        numbers.add(13);
        numbers.add(24);
        numbers.add(43);
        numbers.add(24); // Duplicate element, will not be added
        numbers.add(41);
        numbers.add(55);

        // Display the elements in the set
        System.out.println("Set elements: " + numbers);

        // Check if a specific element is present
        int elementToCheck = 24;
        if (numbers.contains(elementToCheck)) {
            System.out.println(elementToCheck + " is present in the set.");
        } else {
            System.out.println(elementToCheck + " is not present in the set."); 
        }

        // Remove an element from the set
        numbers.remove(43);
        System.out.println("Set elements after removing 43: " + numbers);

    }
}
