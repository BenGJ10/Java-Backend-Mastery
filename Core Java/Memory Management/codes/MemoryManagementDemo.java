class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void displayDetails() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class MemoryManagementDemo {
    // main() methods are stored in Stack Memory, this is a scope
    // Within a scope, variables and references are stored in Stack Memory
    public static void main(String[] args) {
        
        int houseNumber = setHouseNumber(599); // Primitive stored in Stack Memory
        
        // The object person1 is stored in Heap Memory and it's reference is stored in Stack Memory
        // This is a strong reference: it prevents the object from being garbage collected
        Person person1 = new Person("Ben", 20);

        String city = "Mavelikara"; // String literal stored in String Pool (part of Heap Memory)

        String state = new String("Kerala"); // String object stored in Heap Memory 

        person1.displayDetails();
        System.out.println("House Number: " + houseNumber);
        System.out.println("City: " + city);
        System.out.println("State: " + state);

        // Garbage Collector will clean up unreferenced objects in Heap Memory. It runs periodically.
        System.gc(); // Suggests JVM to run Garbage Collector
    }    

    // This will be a separate stack frame in Stack Memory
    private static int setHouseNumber(int number) {
        int houseNumber = number; // Primitive stored in Stack Memory
        return houseNumber;
    } // When the method ends, its stack frame is removed in LIFO manner
}
