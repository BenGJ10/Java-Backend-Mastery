// Suppose you are having an Interface and a Class with the same method.
// A subclass implements the interface and extends the class.
// What will happen if both the interface and the class have a method with the same signature?

interface MyInterface {
    default void display(){
        System.out.println("Display from MyInterface");
    }
}

class MyClass {
    public void display(){
        System.out.println("Display from MyClass");
    }
}

class MySubClass extends MyClass implements MyInterface {
    // No need to override display() method
}
public class ClassInterfaceDemo {
    public static void main(String[] args) {
        MySubClass obj = new MySubClass();
        obj.display(); // This will call the display() method from MyClass
    }   
}
