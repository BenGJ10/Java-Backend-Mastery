// Normally we could not define methods in interfaces prior to Java 8.
// However, Java 8 introduced default and static methods in interfaces, allowing us to add new methods without breaking existing implementations.

interface MyInterface {
    // Abstract method (must be implemented by implementing classes)
    void abstractMethod();
    
    // Default method (provides a default implementation)
    default void defaultMethod() {
        System.out.println("This is a default method in the interface.");
    }
    
    // Static method (belongs to the interface, not to instances)
    static void staticMethod() {
        System.out.println("This is a static method in the interface.");
    }
}

public class InterfaceDemoJava8 {
    public static void main(String args[]){
        MyInterface obj = new MyInterface() {
            // Implementing the abstract method
            public void abstractMethod() {
                System.out.println("Abstract method implementation.");
            }
        };
        
        // Calling the implemented abstract method
        obj.abstractMethod();  // Output: Abstract method implementation.
        
        // Calling the default method
        obj.defaultMethod();   // Output: This is a default method in the interface.
        
        // Calling the static method from the interface
        MyInterface.staticMethod(); // Output: This is a static method in the interface.
    }   
}
