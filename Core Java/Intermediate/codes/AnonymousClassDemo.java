/*
Anonymous Classes are a type of inner class that do not have a name and are used to instantiate objects with certain "extras" such as method overrides or 
implementing interfaces on the fly. They are useful when you need to create a one-time-use class that won't be reused elsewhere in your codebase.
*/

class OuterClass {
    void display() {
        System.out.println("OuterClass display method");
    }
}

// Suppose we need to override the display method of the OuterClass
// Normally, we would create a subclass, but it would be cumbersome for one-time use.
// Instead, we can use an anonymous class to achieve this.

public class AnonymousClassDemo {
    public static void main(String args[]){
        OuterClass obj = new OuterClass() {
            // Overriding the display method
            void display() {
                System.out.println("Anonymous Class display method");
            }
        };
        
        // Calling the overridden method
        obj.display();  // Output: Anonymous Class display method
    }    
}

// In total this code would create 3 files when compiled:
// 1. OuterClass.class
// 2. AnonymousClassDemo.class
// 3. AnonymousClassDemo$1.class (This is the anonymous class created inside the main method)