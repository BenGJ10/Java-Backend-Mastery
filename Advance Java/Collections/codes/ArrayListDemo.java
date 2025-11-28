import java.util.ArrayList;

public class ArrayListDemo {
    public static void main(String[] args) {
        // Create an ArrayList to store String elements
        ArrayList<String> fruits = new ArrayList<String>();
        // Add elements to the ArrayList
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");
        fruits.add("Fig");
        fruits.add("Grape");
        
        // Display the elements in the ArrayList
        System.out.println("Fruits in the list: " + fruits);
        
        // Remove an element from the ArrayList
        fruits.remove("Banana");
        fruits.remove(3); // Removes the element at index 3 (which is "Date")

        // Display the elements after removal
        System.out.println("Fruits after removal: " + fruits);

        // Updating an element in the ArrayList
        fruits.set(0, "Mango");
        System.out.println("Fruits after update: " + fruits);

        // Checking if the ArrayList contains a specific element
        boolean hasApple = fruits.contains("Apple");
        System.out.println("Does the list contain Apple? " + hasApple);

        // ArrayList can also store custom objects and null values
        System.out.println("\nDemonstrating ArrayList with mixed types and null values:");
        ArrayList<Object> mixedList = new ArrayList<Object>();
        mixedList.add("Hello");
        mixedList.add(42);
        mixedList.add(null);
        
        for (Object item : mixedList) {
            System.out.println("Item: " + item);
        }

        // Size of the ArrayList
        System.out.println("Size of the mixed list: " + mixedList.size());
    }
}


        