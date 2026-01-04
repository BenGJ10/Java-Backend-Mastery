import java.util.Map;
import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        
        // Creating a HashMap
        Map<Integer, String> hashMap = new HashMap<>();
        
        // Use put() method to add key-value pairs to the HashMap
        hashMap.put(1, "Banana");
        hashMap.put(2, "Apple");
        hashMap.put(3, "Mango");

        // Use putIfAbsent() method to add a key-value pair only if the key is not already present
        hashMap.putIfAbsent(2, "Orange"); // This will not add since key 2 is already present
        hashMap.putIfAbsent(4, "Grapes"); // This will add since key 4 is not present

        // Display the HashMap
        // Use entrySet() method to get a set view of the mappings contained in the map
        for(Map.Entry<Integer, String> entryMap : hashMap.entrySet()){
            Integer key = entryMap.getKey();
            String value = entryMap.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }

        // Check if the hashMap is empty
        System.out.println("Is the HashMap empty? " + hashMap.isEmpty());

        // Get the size of the HashMap
        System.out.println("Size of the HashMap: " + hashMap.size());

        // Get value associated with a specific key
        /*  How get() method works:
            1. It takes the key as an argument.
            2. It computes the hash code of the key to determine the bucket location.
            3. It computes the modulus of the hash code with the number of buckets to find the correct bucket.
            4. It searches through the linked list (or tree) in that bucket to find the entry with the matching key.
            5. If the key is found, it returns the associated value; otherwise, it returns null.
         */
        System.out.println("Value for key 3: " + hashMap.get(3));

        // Use getOrDefault() method to get the value for a key, or return a default value if the key is not found
        System.out.println("Value for key 5 (default): " + hashMap.getOrDefault(5, "Default Value"));
        
        // Remove a key-value pair using remove() method
        hashMap.remove(1); // Removes the entry with key 1

        // Iterate through the HashMap using forEach() method
        hashMap.forEach((key, value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });

        // Get all values using values() method
        System.out.println("Values in the HashMap: " + hashMap.values());
        
    }    
}
