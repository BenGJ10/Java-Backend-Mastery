/*
The need for interfaces arises from the need to define a contract that classes can implement, allowing for polymorphism and code reusability.
Interfaces allow different classes to be treated uniformly based on the methods they implement, regardless of their specific implementations.
It is a design principle that promotes loose coupling and enhances flexibility in code design.
*/

interface Computer{ // Interfaces are basically abstract classes with only abstract methods
    public static final String company = "GJ Solutions"; // Constants in interfaces are implicitly public, static, and final
    void runCode();
    void needPower();
}

class Desktop implements Computer {
    public void runCode() {
        System.out.println("Running code on Desktop");
    }
    
    public void needPower() {
        System.out.println("Desktop needs power supply to run!");
    }
}

class Laptop implements Computer {
    public void runCode() {
        System.out.println("Running code on Laptop");
    }
    
    public void needPower() {
        System.out.println("Laptop does not need power supply to run!");
    }
}

class Developer{
    public void code(Computer computer) {
        System.out.println("Developer is coding for: " + Computer.company);
        computer.runCode();
        computer.needPower();
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Developer developer = new Developer();
        
        Computer desktop = new Desktop();
        Computer laptop = new Laptop();
        
        developer.code(desktop);
        developer.code(laptop);
    }    
}
