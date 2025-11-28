import java.util.TreeSet;
import java.util.Iterator;

public class TreeSetDemo {
    public static void main(String[] args) {
        // Create a TreeSet to store Integer values
        TreeSet<Integer> treeSet = new TreeSet<>();

        // Add elements to the TreeSet
        treeSet.add(5);
        treeSet.add(2);
        treeSet.add(8);
        treeSet.add(1);
        treeSet.add(4);

        // Iterate through the TreeSet
        System.out.print("Iterating through TreeSet: ");
        
        Iterator<Integer> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        // Remove an element from the TreeSet
        treeSet.remove(4);
        System.out.println("\nTreeSet after removing 4: " + treeSet);

        // Check if an element exists in the TreeSet
        boolean containsFive = treeSet.contains(5);
        System.out.println("TreeSet contains 5: " + containsFive);
    }    
}
