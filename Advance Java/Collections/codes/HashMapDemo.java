import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        // Create a Map to store key-value pairs
        Map<String, Integer> students = new HashMap<>();
        
        // Adding key-value pairs to the Map
        students.put("Ben", 85);
        students.put("Mani", 90);
        students.put("Peven", 78);    
        students.put("Anna", 92);
        students.put("Pothen", 88);
        students.put("Peven", 80); // This will update Peven's score
        
        // Display the Map
        for(String name : students.keySet()) {
            System.out.println(name + ": " + students.get(name));
        }
        
        // Oh! I mispelled Peven's name and again miscalculated his marks. Let's correct it.
        if(students.containsKey("Peven")) {
            Integer score = students.remove("Peven");
            students.put("Pavan", score);
            students.replace("Pavan", 80, 91); // Correcting the score
        }
        
        System.out.println("\nAfter correcting the name:");
        for(String name : students.keySet()) {
            System.out.println(name + ": " + students.get(name));
        }
    }
}
