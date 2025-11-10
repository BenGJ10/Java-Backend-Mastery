class MyObject {
    int value;

    MyObject(int value) {
        this.value = value;  // stored in heap
    }

    public void methodExample() {
        int localValue = 30; // local primitive stored in stack
        System.out.println("Inside methodExample(), localValue = " + localValue);
    }
}

public class StackAndHeapDemo {

    public static void main(String[] args) {
        System.out.println("===== STACK & HEAP MEMORY DEMO =====");

        MyObject obj1 = new MyObject(20);
        // obj1 (reference) → stored in stack
        // new MyObject(20) → actual object stored in heap

        System.out.println("obj1.value stored in heap: " + obj1.value);

        modifyObject(obj1);  
        // Passing reference → reference copied to stack
        // Still points to SAME heap object

        System.out.println("After modifyObject(), obj1.value = " + obj1.value);

        System.out.println("-----------------------------------");

        // Demonstrating multiple references
        MyObject obj2 = new MyObject(50);
        MyObject obj3 = obj2;  
        // obj3 reference (stack) → points to same heap object as obj2

        System.out.println("Before change, obj2.value = " + obj2.value);
        System.out.println("Before change, obj3.value = " + obj3.value);

        obj3.value = 100;  // modifying heap object through second reference

        System.out.println("After change through obj3:");
        System.out.println("obj2.value = " + obj2.value); // also updates!
        System.out.println("obj3.value = " + obj3.value);

        System.out.println("-----------------------------------");

        // Method call demonstrating new stack frame creation
        System.out.println("Calling methodExample() on obj1...");
        obj1.methodExample();  // new stack frame created, destroyed after return

        System.out.println("===== END OF PROGRAM =====");
    }

    public static void modifyObject(MyObject obj) {
        // obj is a copy of reference → stored in stack
        // But it points to the same heap object as obj1

        System.out.println("Inside modifyObject(), modifying heap object...");
        obj.value = 999; // modifies original heap object
    }
}
