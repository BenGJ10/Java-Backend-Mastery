class Print<K, V> {
    K key;
    V value;

    Print(K key, V value) {
        this.key = key;
        this.value = value;
    }

    void display() {
        System.out.println("Key: " + key + ", Value: " + value);
    }
}

public class GenericClassInheritance {
    public static void main(String[] args) {
        // Creating an instance of Print with String and Integer types
        Print<String, Integer> printInstance = new Print<String, Integer>("Age", 25);
        printInstance.display();

        // Creating an instance of Print with Integer and Double types
        Print<Integer, Double> anotherInstance = new Print<>(1, 99.99);
        anotherInstance.display();
    }
}