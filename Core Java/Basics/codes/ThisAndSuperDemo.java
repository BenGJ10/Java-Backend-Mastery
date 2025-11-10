// Every class in Java extends `Object` by default.
// `super` keyword is used to refer to the immediate parent class object.
// It can be used to access parent class methods, variables, and constructors.

class A extends Object {
    A(){
        System.out.println("Inside A's constructor");
    }

    A(int x){
        System.out.println("Inside A's parameterized constructor with value: " + x);
    }
}

class B extends A{
    B(){
        // Implicit call to super() happens here if not specified
        System.out.println("Inside B's constructor");
    }
    B(int y){
        super(y); // Explicit call to superclass's parameterized constructor
        System.out.println("Inside B's parameterized constructor with value: " + y);
    }
}

public class ThisAndSuperDemo {
    public static void main(String args[]){
    
        // Demonstrates implicit super() call
        B obj1 = new B();

        // Demonstrates explicit super(y) constructor call
        B obj2 = new B(50);

        // Java doesn't have destructors like cpp
    }
}
