// We can create abstract classes and methods using the abstract keyword
// An abstract class cannot be instantiated, and an abstract method must be implemented by subclasses
// An abstract class can have both abstract and non-abstract methods

abstract class Car{
    // Abstract method (does not have a body)
    public abstract void drive();

    // Non-abstract method (has a body)
    public final void stop(){
        System.out.println("Car has stopped.");
    }
}

class Sedan extends Car{ // Concrete subclass extending the abstract class
    // Implementing the abstract method
    public void drive(){
        System.out.println("Driving a sedan.");
    }
}

public class AbstractDemo {
    public static void main(String[] args) {
        
        // Car car = new Car(); -> This would cause an error because we cannot instantiate an abstract class
        
        Car myCar = new Sedan(); // Creating an object of the subclass (concrete class)
        myCar.drive(); // Calling the implemented abstract method
        myCar.stop();  // Calling the non-abstract method

        // Another way to use abstract classes is through anonymous classes
        Car myAnonymousCar = new Car() {    
            public void drive() {
                System.out.println("Driving an anonymous car.");
            }
        };
        myAnonymousCar.drive(); // Calling the implemented abstract method in the anonymous class
        myAnonymousCar.stop();  // Calling the non-abstract method
    }
}
