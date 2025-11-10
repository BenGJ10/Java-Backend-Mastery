// By default, Java provides a no-argument constructor if none is defined.
// However, if any constructor is defined, the default is not provided.
// Java does not provide a built-in copy constructor, but you can manually implement one. It is often used in design patterns like Singleton.

class Human {
    String name;
    int age;

    // Default constructor
    public Human() {
        this.name = "Unknown";
        this.age = 0;
    }

    // Parameterized constructor
    public Human(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class ConstructorsDemo {
    public static void main(String[] args) {
        
        // Using default constructor
        Human human1 = new Human();
        System.out.println("Human1: Name = " + human1.name + ", Age = " + human1.age);

        // Using parameterized constructor
        Human human2 = new Human("Ben", 20);
        System.out.println("Human2: Name = " + human2.name + ", Age = " + human2.age);

        // Copy constructor (manual implementation)
        Human human3 = new Human(human2.name, human2.age);
        System.out.println("Human3: Name = " + human3.name + ", Age = " + human3.age);  
    }
}
