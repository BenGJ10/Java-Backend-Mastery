// We can make use of Generics methods when we want to create a method that can operate on different data types
// without the need to overload the method for each type.
// This enhances code reusability and type safety.

public class GenericsMethodDemo {
    // Generic method to display array elements
    public static <T> void displayArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Creating an Integer array
        Integer[] intArray = {1, 2, 3, 4, 5};
        System.out.print("Integer Array: ");
        displayArray(intArray);

        // Creating a String array
        String[] stringArray = {"Hello", "Generics", "in", "Java"};
        System.out.print("String Array: ");
        displayArray(stringArray);

        // Creating a Double array
        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
        System.out.print("Double Array: ");
        displayArray(doubleArray);
    }
}
