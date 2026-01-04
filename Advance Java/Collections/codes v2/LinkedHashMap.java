/* The core difference between HashMap and LinkedHashMap is that LinkedHashMap maintains a
doubly-linked list running through all of its entries. This linked list defines the iteration
ordering, which is normally the order in which keys were inserted into the map (insertion-order).
This allows LinkedHashMap to provide predictable iteration order, which can be useful in scenarios
where the order of elements matters, such as caching mechanisms or when displaying data to users.
*/

// It has two types of ordering: insertion-order and access-order.
// Insertion-order: The order in which keys were inserted into the map.
// Access-order: The order in which keys were last accessed, from least-recently accessed to most-recently accessed.

import java.util.Map;

public class LinkedHashMap {
    public static void main(String[] args) {
        // In the Entry of LinkedHashMap, apart from key, value, and hash, there are two additional pointers:
        // before: Points to the previous entry in the linked list.
        // after: Points to the next entry in the linked list.
        
        System.out.println("Using LinkedHashMap:");
        Map<Integer, String> linkedHashMap = new java.util.LinkedHashMap<>(16, 0.75f, true); // false for insertion-order
        linkedHashMap.put(1, "Man City");
        linkedHashMap.put(7, "Liverpool");
        linkedHashMap.put(2, "Arsenal");
        linkedHashMap.put(18, "Man United");
        linkedHashMap.forEach((Integer key, String value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });
        
        // The same code with HashMap
        System.out.println("\nUsing HashMap:");
        Map<Integer, String> hashMap = new java.util.HashMap<>();
        hashMap.put(1, "Man City");
        hashMap.put(7, "Liverpool");
        hashMap.put(2, "Arsenal");
        hashMap.put(18, "Man United");
        hashMap.forEach((Integer key, String value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });

        // We can see the difference in the order of elements printed by LinkedHashMap and HashMap.

        // Understanding access-order:
        System.out.println("\nAccessing some elements in LinkedHashMap:");
        linkedHashMap.get(2); // Accessing key 2
        linkedHashMap.get(1); // Accessing key 1
        linkedHashMap.forEach((Integer key, String value) -> {
            System.out.println("Key: " + key + ", Value: " + value);
        });
    }    
}
