import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ListDemo {
    public static void main(String[] args) {
        
        List<Integer> list1 = new ArrayList<>();
        // Adding elements to the List
        list1.add(10);
        list1.add(20);
        list1.add(30);
        list1.add(40);
        System.out.println("List after additions: " + list1);

        // Creating another List and adding elements
        List<Integer> list2 = new ArrayList<>();
        list2.add(0, 15);
        list2.add(1, 25);
        // Adding all elements of list2 to list1 at index 2
        list1.addAll(2, list2);
        System.out.println("List after adding all elements: " + list1);
        
        // Sorting the List
        Collections.sort(list1, (a, b) -> b - a); // Sorting in descending order
        System.out.println("List after sorting: " + list1);

        // Replacing all values with it's double
        // replaceAll(UnaryOperator<T> operator) this is a functional interface that takes a single argument and returns a single value.
        list1.replaceAll(value -> value * 2);
        System.out.print("List after replacing all values with double: ");
        list1.forEach(value -> System.out.print(value + " "));

        // Retrieving element at index 3
        int elementAtIndex3 = list1.get(3);
        System.out.println("\nElement at index 3: " + elementAtIndex3);
        // Replacing element at index 3
        list1.set(3, 1000);
        System.out.println("List after setting index 3 to 1000: " + list1);
        System.out.println("Index of element 1000: " + list1.indexOf(1000));

        // Using ListIterator to traverse the list in reverse order
        // We can use ListIterator to traverse the list in both forward and backward direction.
        System.out.print("List elements in reverse order: ");
        ListIterator<Integer> listIterator = list1.listIterator(list1.size());
        while (listIterator.hasPrevious()) {
            System.out.print(listIterator.previous() + " ");
        }

        // Getting a sublist from index 2 to 5, fromIndex is inclusive and toIndex is exclusive
        List<Integer> subList = list1.subList(2, 5);
        System.out.println("\nSublist from index 2 to 5: " + subList);
        subList.add(10);

        // Adding an element to the sublist also reflects in the original list
        System.out.println("Original list after adding 10 to sublist: " + list1);
    }    
}
